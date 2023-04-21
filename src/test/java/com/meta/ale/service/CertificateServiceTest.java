package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CertificateServiceTest {

    @Autowired
    private CertificateService certificateService;

    @Test
    void certificateListTest() throws Exception {
        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);

        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");


        certificateService.getCertList(criteria, userDto.getUserId());
    }

    @Test
    void certificateDetailTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        Long certId = 1L;


        certificateService.getCertCompared(certId, userDto.getUserId());
    }


}





















