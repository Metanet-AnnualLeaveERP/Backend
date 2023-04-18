package com.meta.ale.api;

import com.meta.ale.domain.CertificateDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcReqDto;
import com.meta.ale.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CertificateRestController {

    private final CertificateService certificateService;

    /*증명서 내역 조회 (페이징 처리)*/
    @GetMapping("/certificates")
    public Map<String, Object> certificateList(@AuthenticationPrincipal UserDto user, Criteria cri) {
        /* ADMIN or EMP 판별
        userId == 0 -> admin */
        return certificateService.getCertList(cri, user.getUserId());
    }

    /*증명서 내역 상세 조회*/
    @GetMapping("/certificates/{certificates_id}")
    public ResponseEntity<Object> certificateDetail(@PathVariable("certificates_id") Long certId,
                                                    @AuthenticationPrincipal UserDto user) {
        CertificateDto dto = certificateService.getCertCompared(certId, user.getUserId());

        if (dto != null) {
            dto.getVcReqDto().getEmpDto().setUserDto(null); // userDto
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        // 자기가 작성하지 않은 휴가 신청에 접근하면 403 에러와 null 반환
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다");
    }

    /*휴가 확인서 (증명서) 발급 요청*/
    @PostMapping("/certificates")
    public ResponseEntity createCertificate(@RequestBody CertificateDto dto) {
        certificateService.createCert(dto);

        // 현재 경로에 생성된 reqId 를 덧붙여서 반환해 준다
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getCertId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
