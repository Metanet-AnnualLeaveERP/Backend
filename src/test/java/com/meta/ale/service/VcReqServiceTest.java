package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcReqDto;
import com.meta.ale.mapper.VcReqMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


@SpringBootTest
class VcReqServiceTest {

    @Autowired
    VcReqService vcReqService;

    @Test
    void getVcReqListTest() {
        Criteria cri = new Criteria();
        UserDto user = new UserDto();
        user.setUserId(1L);
        Map<String, Object> map = vcReqService.getVcReqList(cri, user);

        for (Map.Entry<String, Object> entrySet : map.entrySet()) {
            System.out.println(entrySet.getKey() + " : " + entrySet.getValue());
        }
    }

    // role admin
    @Test
    void getVcReqComparedTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(5L);
        userDto.setRole("ROLE_ADMIN");

        Long reqId = 1L;

        vcReqService.getVcReqCompared(reqId, userDto);
    }

    // role mgr
    @Test
    void getVcReqComparedTest2() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(5L);
        userDto.setRole("ROLE_MGR");

        Long reqId = 1L;

        vcReqService.getVcReqCompared(reqId, userDto);
    }

    // dto == null
    @Test
    void getVcReqComparedTest3() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(5L);
        userDto.setRole("ROLE_EMP");

        Long reqId = 500L;

        vcReqService.getVcReqCompared(reqId, userDto);
    }

    // currUserId == dbUserId ? dto : null;에서 dto
    @Test
    void getVcReqComparedTest4() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(42L);
        userDto.setRole("ROLE_EMP");

        Long reqId = 1L;

        vcReqService.getVcReqCompared(reqId, userDto);
    }

    // currUserId == dbUserId ? dto : null;에서 null
    @Test
    void getVcReqComparedTest5() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(43L);
        userDto.setRole("ROLE_EMP");

        Long reqId = 1L;

        vcReqService.getVcReqCompared(reqId, userDto);
    }

    @Test
    void getVcReqTest() throws Exception {
        Long reqId = 1L;
        vcReqService.getVcReq(reqId);
    }

    @Test
    void teamApprovalList() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);
        criteria.setKeyword("테스트");

        vcReqService.getApprovalVcRequestList(userDto, criteria);
    }

    @Test
    void myTeamVacationTest() throws Exception{
        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        vcReqService.findMyTeamVacation(userDto);
    }

    @Test
    void entireTeamRemainVcToTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        vcReqService.calcRemainTOByVcReqs(userDto);
    }

    @Test
    void updateVcReqStatusTest() throws Exception {
        VcReqDto vcReqDto = new VcReqDto();

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2023-04-20");

        vcReqDto.setReqId(1L);
        vcReqDto.setStatus("취소");
        vcReqDto.setAprvDate(testDate);
        vcReqDto.setDeniedComments("테스트");

        vcReqService.updateVcReqStatus(vcReqDto);
    }

    // vcReq.getReqId() == null
    @Test
    void approvalVcRequestStatusTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        Long reqId = 500L;
        String status = "승인";
        String comment = "테스트";

        vcReqService.approvalVcRequestStatus(userDto, reqId, status, comment);
    }

    // 승인
    @Test
    void approvalVcRequestStatusTest2() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(42L);
        userDto.setEmpNum("1234");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        Long reqId = 1L;
        String status = "승인";
        String comment = "테스트";

        vcReqService.approvalVcRequestStatus(userDto, reqId, status, comment);
    }

    // 반려
    @Test
    void approvalVcRequestStatusTest3() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(42L);
        userDto.setEmpNum("1234");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        Long reqId = 1L;
        String status = "반려";
        String comment = "테스트";

        vcReqService.approvalVcRequestStatus(userDto, reqId, status, comment);
    }

    // 반려
    @Test
    void approvalVcRequestStatusTest4() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(42L);
        userDto.setEmpNum("1234");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        Long reqId = 1L;
        String status = "테스트";
        String comment = "테스트";

        vcReqService.approvalVcRequestStatus(userDto, reqId, status, comment);
    }

    // if (role.equals("ROLE_MGR"))
    @Test
    void getApprovalVcRequestListTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(81L);
        userDto.setEmpNum("2121003");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_MGR");

        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);
        criteria.setKeyword("");
        vcReqService.getApprovalVcRequestList(userDto, criteria);
    }

    @Test
    void getApprovalVcRequestListTest2() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(42L);
        userDto.setEmpNum("1234");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);
        criteria.setKeyword("");
        vcReqService.getApprovalVcRequestList(userDto, criteria);
    }

//    @Test
//    void createVcReq() {
//        VcReqDto dto = new VcReqDto();
//
//        // 시작일
//        Date startDate = new Date();
//        startDate.setDate(startDate.getDate() + 3);
//        dto.setStartDate(startDate);
//
//        // 종료일
//        Date endDate = new Date();
//        endDate.setDate((dto.getStartDate().getDate()) + 1);
//        dto.setEndDate(endDate);
//
////        dto.setVcType("연차");
//        dto.setReqDays(2.0);
//        dto.setComments(null);
//        dto.setStatus("자동승인");
//        dto.setAprvDate(null);
//        dto.setFilePath(null);
//
//        // emp
//        EmpDto emp = new EmpDto();
//        emp.setEmpId(2L);
//        dto.setEmpDto(emp);
//
////        vcReqService.createVcReq(dto);
//    }

//    @Test
//    void updateVcReqStatus() {
//        VcReqDto dto = vcReqMapper.getVcReq(2L);
//        dto.setStatus("취소");
//        System.out.println(vcReqService.updateVcReqStatus(dto));
//    }


//    @Test
//    void approvalVcRequestList() {
//        Optional<UserDto> userDto = userService.getByEmpNum("admin");
//        System.out.println(userDto.get());
//        Criteria cri = new Criteria();
////        cri.setKeyword("취소");
//        vcReqService.getApprovalVcRequestList(userDto.get(), cri);


}