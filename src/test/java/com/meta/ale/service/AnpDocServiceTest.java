package com.meta.ale.service;

import com.meta.ale.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class AnpDocServiceTest {

    @Autowired
    private AnpDocService anpDocService;

    @Test
    void getListAnpDocTest() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);

        anpDocService.getListAnpDoc(userDto, criteria);
    }


    @Test
    void getAnpDocTest() throws Exception {
        Long docId = 10L;

        anpDocService.getAnpDoc(docId);
    }

    @Test
    void insertAnpDocScheduler() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        VcTypeDto vcTypeDto =
//                new VcTypeDto(1L, "연차", "기본연차",
//                        1L, 10L, testDate3, null);
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(17L);
//        userDto.setEmpNum("2222");
//        userDto.setEnabled(1L);
//        userDto.setRole("ROLE_EMP");
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(1L);
//        deptDto.setDeptName("개발팀");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(16L, "짱구", testDate, testDate2, "팀원",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, 5L, deptDto);
//
//        GrantedVcDto grantedVcDto = new GrantedVcDto();
//        grantedVcDto.setGrantedDate(testDate);
//        grantedVcDto.setExpiredDate(testDate2);
//        grantedVcDto.setVcDays(10L);
//        grantedVcDto.setRemainDays(2.5);
//        grantedVcDto.setVcTypeDto(vcTypeDto);
//        grantedVcDto.setEmpDto(empDto);

        anpDocService.insertAnpDocScheduler();

    }


    // db에 anp_doc테이블에 docId 확인하고 테스트해야함
    // 추가를 하던지 밑에 변수를 변경하던지
    @Test
    void deleteAnpDocTest1() throws Exception {
        Long docId = 3L;

        anpDocService.deleteAnpDoc(docId);
    }

    @Test
    void deleteAnpDocTest2() throws Exception {
        Long docId = 100L;

        anpDocService.deleteAnpDoc(docId);
    }

    @Test
    void insertAnpDocManuallyTest() throws Exception {

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2022-03-20");
        Date testDate2 = date.parse("2023-03-20");
        Date testDate3 = date.parse("2023-03-20");

        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        DeptDto deptDto = new DeptDto();
        deptDto.setDeptId(1L);
        deptDto.setDeptName("개발팀");
        deptDto.setVcTo(10L);

        EmpDto empDto = new EmpDto(16L, "짱구", testDate, testDate2, "팀원",
                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, 5L, deptDto);

        AnpDocDto anpDocDto = new AnpDocDto();
        anpDocDto.setTotalAnv(20);
        anpDocDto.setUsedAnv(2);
        anpDocDto.setRemainAnv(18);
        anpDocDto.setOccurDate(testDate3);
        anpDocDto.setAnvOccurDate(testDate);
        anpDocDto.setEmpDto(empDto);

        anpDocService.insertAnpDocManually(anpDocDto);
    }

}
