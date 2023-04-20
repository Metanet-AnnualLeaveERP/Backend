package com.meta.ale.mapper;

import com.meta.ale.domain.CertificateDto;
import com.meta.ale.domain.VcReqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CertificateMapper {

    /*증명서 내역 조회*/
    public List<CertificateDto> getCertList(HashMap<String, Object> hashMap);

    /*증명서 내역 상세 조회*/
    public CertificateDto getCert(Long certId);

    /*휴가 확인서 (증명서) 발급 요청*/
    public void insertCert(CertificateDto dto);

    /*증명서 개수 (페이징 처리용)*/
    public Long getCertCount(Long userId);
}
