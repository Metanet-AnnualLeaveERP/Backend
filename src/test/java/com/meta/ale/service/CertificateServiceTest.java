package com.meta.ale.service;

import com.meta.ale.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class CertificateServiceTest {

    @Autowired
    private CertificateService certificateService;

    @Test
    void getCertListTest() throws Exception {
        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);

        Long userId = 100L;
        certificateService.getCertList(criteria, userId);
    }

    @Test
    void getCertListTest2() throws Exception {
        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);

        Long userId = 42L;
        certificateService.getCertList(criteria, userId);
    }

    //관리자면
    @Test
    void getCertComparedTest() throws Exception {

        Long certId = 1L;
        Long userId = 0L;

        certificateService.getCertCompared(certId, userId);
    }

    //dto == null
    @Test
    void getCertComparedTest2() throws Exception {

        Long certId = 10L;
        Long userId = 42L;

        certificateService.getCertCompared(certId, userId);
    }

    @Test
    void getCertComparedTest3() throws Exception {

        Long certId = 1L;
        Long userId = 42L;

        certificateService.getCertCompared(certId, userId);
    }

    @Test
    void createCertTest() throws Exception {

        EmpDto empDto = new EmpDto();
        empDto.setEmpId(40L);

        VcReqDto vcReqDto = new VcReqDto();
        vcReqDto.setReqId(1L);

        CancelDto cancelDto = new CancelDto();
        cancelDto.setCancelId(2L);

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2022-03-20");

        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setIssuedDate(testDate);
        certificateDto.setPurpose("하하핫");
        certificateDto.setEmpDto(empDto);
        certificateDto.setVcReqDto(vcReqDto);
        certificateDto.setCancelDto(cancelDto);

        certificateService.createCert(certificateDto);
    }


}





















