package com.meta.ale.service;

import com.meta.ale.domain.CertificateDto;
import com.meta.ale.domain.Criteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CertificateService {

    /*증명서 내역 조회*/
    public Map<String, Object> getCertList(Criteria cri, Long userId);

    /*증명서 내역 상세 조회*/
    public CertificateDto getCertCompared(Long certId, Long currUserId);

    /*휴가 확인서 (증명서) 발급 요청*/
    public void createCert(CertificateDto dto);

}
