package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.DeptDto;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
public class EmpServiceTests {

    @Autowired
    private EmpService empService;

    @Test
    void findEmpOverOneYrTest() throws Exception {
        empService.findEmpOverOneYr();
    }

    @Test
    void findEmpOneYrTest() throws Exception{
        empService.findEmpOneYr();
    }

    @Test
    void findEmpUnderOneYrTest() throws Exception {
        empService.findEmpUnderOneYr();
    }


    // 유저 지워짐 테스트 완료.
//    @Test
//    void deleteEmpOverTwoYrLeaveDate() throws Exception {
//        empService.deleteEmpOverTwoYrLeaveDate();
//    }

    @Test
    void getEmpInfoTest() throws Exception {
        Long empId = 45L;
        empService.getEmpInfo(empId);
    }

    @Test
    void getEmpList() throws Exception {
        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);
        criteria.setKeyword("전체,이름,전체");

        empService.getEmpList(criteria);
    }

    @Test
    void managerInfoTest() throws Exception {
        Long mgrId = 1L;
        empService.getEmpByMgrId(mgrId);
    }

    @Test
    void myInfoTest() throws Exception {
        Long userId = 17L;
        empService.findEmpByUserId(userId);
    }

    //userDto.getPwd().equals("")
    @Test
    void modifyInfoTest() throws Exception {
        UserDto userDto = new UserDto();

        userDto.setPwd("");

        EmpDto empDto = new EmpDto();
        empDto.setEmpId(51L);
        empDto.setPEmail("cr7@gmail.com");

        empService.modifyInfo(userDto, empDto);

    }

    @Test
    void modifyInfoTest2() throws Exception {
        UserDto userDto = new UserDto();

        userDto.setPwd("1111");

        EmpDto empDto = new EmpDto();
        empDto.setEmpId(51L);
        empDto.setPEmail("cr7@gmail.com");

        empService.modifyInfo(userDto, empDto);

    }

    @Test
    void selectListByDeptIdTest() throws Exception {
        empService.selectListByDeptId(1L);
    }

//    @Test
//    void modifyEmpTest() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(17L);
//        userDto.setEmpNum("2222");
//        userDto.setEnabled(1L);
////        userDto.setRole("ROLE_EMP");
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(1L);
//        deptDto.setDeptName("개발팀");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(16L, "짱구", testDate, testDate2, "팀원",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, 5L, deptDto);
//
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest2() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
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
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest3() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(26L);
//        userDto.setEmpNum("3333");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(1L);
//        deptDto.setDeptName("인사팀");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(25L, "맹구", testDate, testDate2, "팀원",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, 5L, deptDto);
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest4() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(50L);
//        userDto.setEmpNum("2305001");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(5L);
//        deptDto.setDeptName("디자인팀");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(49L, "메시", testDate, testDate2, "팀원",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, null, deptDto);
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest5() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(46L);
//        userDto.setEmpNum("1238");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(5L);
//        deptDto.setDeptName("디자인팀");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(45L, "호날두", testDate, testDate2, "팀원",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, null, deptDto);
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest6() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(46L);
//        userDto.setEmpNum("1238");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(5L);
//        deptDto.setDeptName("디자인팀");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(45L, "호날두", testDate, testDate2, "테스트",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, null, deptDto);
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest7() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(46L);
//        userDto.setEmpNum("1238");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(5L);
//        deptDto.setDeptName("개발팀");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(45L, "호날두", testDate, testDate2, "팀장",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, null, deptDto);
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest8() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(46L);
//        userDto.setEmpNum("1238");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(5L);
//        deptDto.setDeptName("법무팀");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(45L, "호날두", testDate, testDate2, "팀장",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, null, deptDto);
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest9() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(46L);
//        userDto.setEmpNum("1238");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(5L);
//        deptDto.setDeptName("영업팀");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(45L, "호날두", testDate, testDate2, "팀장",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, null, deptDto);
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest11() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(85L);
//        userDto.setEmpNum("2308006");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(140L);
//        deptDto.setDeptName("테스트");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(90L, "나나", testDate, testDate2, "팀장",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, null, deptDto);
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest12() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(85L);
//        userDto.setEmpNum("2308006");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(140L);
//        deptDto.setDeptName("테스트");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(90L, "나나", testDate, testDate2, "팀장",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, null, deptDto);
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest13() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(85L);
//        userDto.setEmpNum("2308006");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(142L);
//        deptDto.setDeptName("테스트2");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(90L, "나나", testDate, testDate2, "팀장",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, null, deptDto);
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest14() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(31L);
//        userDto.setEmpNum("5555");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(142L);
//        deptDto.setDeptName("테스트2");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(30L, "잭슨", testDate, testDate2, "팀장",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, null, deptDto);
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest15() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(31L);
//        userDto.setEmpNum("5555");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(144L);
//        deptDto.setDeptName("테스트2");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(30L, "잭슨", testDate, testDate2, "팀장",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, null, deptDto);
//        empService.modifyEmp(empDto);
//    }
//
//    @Test
//    void modifyEmpTest16() throws Exception {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2022-03-20");
//        Date testDate2 = date.parse("2023-03-20");
//        Date testDate3 = date.parse("2023-03-20");
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(39L);
//        userDto.setEmpNum("7777");
//        userDto.setEnabled(1L);
//
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptId(145L);
//        deptDto.setDeptName("테스트5");
//        deptDto.setVcTo(10L);
//
//        EmpDto empDto = new EmpDto(38L, "아만다", testDate, testDate2, "팀원",
//                "aa432@gmail.com", "aa5234@gmail.com", "010-5553-7111", userDto, null, deptDto);
//        empService.modifyEmp(empDto);
//    }



//    @Test
//    public void empOverTwoYrLeaveDate(){
//        try {
//            //크론스케줄러 사원 삭제 테스트 코드
////            empService.deleteEmpOverTwoYrLeaveDate();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }



}
