package com.meta.ale.api;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcReqDto;
import com.meta.ale.service.VcReqService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class VcReqRestController {

    private VcReqService vcReqService;

    /*휴가 신청 내역 조회*/
    @GetMapping("/vacations")
    public Map<String, Object> vcReqList(@RequestParam(required = false, defaultValue = "1") int page,
                                         @RequestParam(required = false, defaultValue = "10") int pagenum,
                                         @AuthenticationPrincipal UserDto user,
                                         Criteria cri) {

        cri.setPageNum(page);
        cri.setAmount(pagenum);

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
            dto.getEmpDto().setUserDto(null);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        // 자기가 작성하지 않은 휴가 신청에 접근하면 403 에러와 null 반환
        // 혹은 커스텀 예외 처리
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다");
// 예외 처리 예시
//        if (user == null) {
//            throw new UserNotFoundException(String.format("ID[%s] not found", id));
//        }
    }

    /*휴가 신청*/
    @PostMapping("/emp/vacations")
    public ResponseEntity createVcReq(@RequestBody VcReqDto dto) {
        vcReqService.createVcReq(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getReqId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /*휴가 결재(승인/반려)*/

    /*휴가 결재 내역 조회*/

}
