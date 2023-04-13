package com.meta.ale.service;

import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTests {

    @Autowired
    MailService mailService;


    /*
    * 테스트용으로 Password
    * */
    @Test
    public void MailToSendFromPEmail() {
        try {
            EmpDto empDto = new EmpDto();
            empDto.setPEmail("sj120712@gmail.com");
            UserDto user = new UserDto();
            user.setPwd("qwer");
            empDto.setUserDto(user);

            mailService.sendToPEmail(empDto, "비밀번호찾기");
        } catch (Exception e) {

        }

    }


//    @Test
//    public void MailToSendFromCEmail() {
//        try {
//            EmpDto empDto = new EmpDto();
//            empDto.setPEmail("qortjdwns120712@gmail.com");
//            mailService.sendToCEmail(empDto,"Title","Text");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
