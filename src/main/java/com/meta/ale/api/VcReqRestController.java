package com.meta.ale.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcReqDto;
import com.meta.ale.service.VcReqService;
import lombok.AllArgsConstructor;
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
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class VcReqRestController {

    private final VcReqService vcReqService;

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
        user.getUserId();

        vcReqService.createVcReq(dto, uploadFiles);

        // 현재 경로에 생성된 reqId 를 덧붙여서 반환해 준다
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getReqId())
                .toUri();
        return ResponseEntity.created(location).build();
//        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }


    /*휴가 결재(승인/반려)*/

    /*휴가 결재 내역 조회*/

}
