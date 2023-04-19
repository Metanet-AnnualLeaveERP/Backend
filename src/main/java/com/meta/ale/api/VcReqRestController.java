package com.meta.ale.api;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcReqDto;
import com.meta.ale.service.VcReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class VcReqRestController {

    private final VcReqService vcReqService;

    /*휴가 신청 내역 조회*/
    @GetMapping("/vacations")
    public Map<String, Object> vcReqList(@AuthenticationPrincipal UserDto user,
                                         @RequestParam String paging, Criteria cri) {
        if (paging.equals("false")) {
            cri.setPageNum(1);
            cri.setAmount(Integer.MAX_VALUE);
        }

        /* ADMIN or EMP 판별
        userId == 0 -> admin */
        return vcReqService.getVcReqList(cri, user.getUserId());
    }

    /*휴가 신청 내역 상세 조회*/
    @GetMapping("/vacations/{vacation_request_id}")
    public ResponseEntity<Object> vcReqDetail(@PathVariable("vacation_request_id") Long reqId,
                                              @AuthenticationPrincipal UserDto user) {
        VcReqDto dto = vcReqService.getVcReqCompared(reqId, user.getUserId());

        if (dto != null) {
            dto.getEmpDto().setUserDto(null); // userDto
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        // 자기가 작성하지 않은 휴가 신청에 접근하면 403 에러와 null 반환
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다");
    }

    /*휴가 신청*/
    @PostMapping(value = "/emp/vacations", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createVcReq(@RequestPart(value = "request") VcReqDto dto,
                                      @RequestPart(value = "uploadFiles", required = false) MultipartFile[] uploadFiles,
                                      @AuthenticationPrincipal UserDto user) throws IOException {
        // 현재 로그인한 사람의 emp 객체를 가지고 오기 위해
        // getEmp 서비스를 호출해서 userId로 emp를 가져온 후
        // vcReq.setEmp 를 해 주어야 함
        try {
            vcReqService.createVcReq(dto, uploadFiles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.toString());
        }

        // 현재 경로에 생성된 reqId 를 덧붙여서 반환해 준다
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getReqId())
                .toUri();
        return ResponseEntity.created(location).build();
//        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }


    /*휴가 결재(승인/반려)*/
    @PutMapping("/manager/vacations/confirm/{vacation_request_id}")
    public ResponseEntity approvalVcReq(@AuthenticationPrincipal UserDto userDto,
                                        @PathVariable("vacation_request_id") Long vcReqId,
                                        @RequestParam("status") String status,
                                        @RequestParam("comment") String comment) {
        // 잘못된 VcReq일 경우
        if (!vcReqService.approvalVcRequestStatus(userDto, vcReqId, status, comment)) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
        return ResponseEntity.ok("정상처리 되었습니다.");
    }

    /*휴가 결재 내역 조회*/
    @GetMapping("/manager/vacations/approval")
    public ResponseEntity teamApprovalList(Criteria cri,@AuthenticationPrincipal UserDto userDto) {
        Map<String, Object> result = vcReqService.getApprovalVcRequestList(userDto, cri);

        return ResponseEntity.ok(result);
    }

    /*팀원 휴가 승인내역 조회 (캘린더용) */
    @GetMapping("/vacations/my-team")
    public ResponseEntity myTeamVacation(@AuthenticationPrincipal UserDto userDto) {
        return ResponseEntity.ok(vcReqService.findMyTeamVacation(userDto));
    }

    /*부서별 잔여 TO (모든 팀원의 휴가들을 날짜별로 to에서 차감)*/
    @GetMapping("/vacations/remain-to")
    public ResponseEntity entireTeamRemainVcTo(@AuthenticationPrincipal UserDto userDto) throws Exception {
        return ResponseEntity.ok(vcReqService.calcRemainTOByVcReqs(userDto));
    }
}
