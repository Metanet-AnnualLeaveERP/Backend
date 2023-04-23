package com.meta.ale.service;

import com.meta.ale.domain.CancelDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcReqDto;
import com.meta.ale.mapper.VcReqMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CancelServiceTest {

    @Autowired
    CancelService cancelService;

    @Test
    void getCancelListTest() throws Exception {
        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);
        criteria.setKeyword("테스트");

        UserDto userDto = new UserDto();
        userDto.setUserId(17L);

        cancelService.getCancelList(criteria, userDto.getUserId());
    }

   @Test
    void getCancelTest() throws Exception {
        Long cancelId = 1L;
        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        cancelService.getCancel(cancelId, userDto);
   }

    @Test
    void getCancelTest2() throws Exception {
        Long cancelId = 1L;
        UserDto userDto = new UserDto();
        userDto.setUserId(42L);
        userDto.setEmpNum("1234");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        cancelService.getCancel(cancelId, userDto);
    }

    @Test
    void getCancelTest3() throws Exception {
        Long cancelId = 11L;
        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        cancelService.getCancel(cancelId, userDto);
    }

    @Test
    void getCancelTest4() throws Exception {
        Long cancelId = 1L;
        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_MGR");

        cancelService.getCancel(cancelId, userDto);
    }
    @Test
    void getCancelTest5() throws Exception {
        Long cancelId = 1L;
        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_ADMIN");

        cancelService.getCancel(cancelId, userDto);
    }


   @Test
    void getApprovalCancelListTest() throws Exception {
       UserDto userDto = new UserDto();
       userDto.setUserId(17L);
       userDto.setEmpNum("2222");
       userDto.setEnabled(1L);
       userDto.setRole("ROLE_EMP");

       Criteria criteria = new Criteria();
       criteria.setPageNum(1);
       criteria.setAmount(10);
       criteria.setKeyword("테스트");

        cancelService.getApprovalCancelList(userDto, criteria);
   }

    @Test
    void getApprovalCancelListTest2() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(26L);
        userDto.setEmpNum("3333");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_MGR");

        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);
        criteria.setKeyword("테스트");

        cancelService.getApprovalCancelList(userDto, criteria);
    }

   @Test
   void updateCancelStatusTest() throws Exception {

       SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
       Date testDate = date.parse("2023-04-29");


        CancelDto cancelDto = new CancelDto();
        cancelDto.setCancelId(1L);
        cancelDto.setCancelStatus("자동취소");

        cancelDto.setResDate(testDate);
        cancelService.updateCancelStatus(cancelDto);
   }

    @Test
    void updateCancelStatusTest2() throws Exception {

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2023-04-29");


        CancelDto cancelDto = new CancelDto();
        cancelDto.setCancelId(10L);
        cancelDto.setCancelStatus("자동취소");

        cancelDto.setResDate(testDate);
        cancelService.updateCancelStatus(cancelDto);
    }

    @Test
    void approvalCancelTest() throws Exception {
        Long cancelId = 1L;
        String status = "";
        String comment = "취소취소";
        cancelService.approvalCancel(cancelId, status, comment);
    }

    @Test
    void approvalCancelTest2() throws Exception {
        Long cancelId = 10L;
        String status = "승인";
        String comment = "취소취소";
        cancelService.approvalCancel(cancelId, status, comment);
    }

    @Test
    void approvalCancelTest3() throws Exception {
        Long cancelId = 3L;
        String status = "승인";
        String comment = "취소취소";
        cancelService.approvalCancel(cancelId, status, comment);
    }
    @Test
    void approvalCancelTest4() throws Exception {
        Long cancelId = 2L;
        String status = "승인";
        String comment = "취소취소";
        cancelService.approvalCancel(cancelId, status, comment);
    }
    @Test
    void approvalCancelTest5() throws Exception {
        Long cancelId = 1L;
        String status = "관리자 대기중";
        String comment = "취소취소";
        cancelService.approvalCancel(cancelId, status, comment);
    }

    // 시작일이 현재일 이후이라면 (아직 안 지남)
    // 수정해야함
//    @Test
//    void createCancel() throws Exception {
//        CancelDto cancelDto = new CancelDto();
//
//        cancelDto.setCancelId(1L);
//        Long reqId = 4L;
//
//        cancelService.createCancel(cancelDto, reqId);
//    }
//
//    // 시작일이 현재일 이후이라면 (아직 안 지남)
//    @Test
//    void createCancel2() throws Exception {
//        CancelDto cancelDto = new CancelDto();
//
//        cancelDto.setCancelId(1L);
//        Long reqId = 4L;
//
//        cancelService.createCancel(cancelDto, reqId);
//    }
//
//    // 시작일이 현재일 이전이라면 (지남)
//    @Test
//    void createCancel3() throws Exception {
//        CancelDto cancelDto = new CancelDto();
//
//        cancelDto.setCancelId(2L);
//        Long reqId = 1L;
//
//        cancelService.createCancel(cancelDto, reqId);
//    }
//
//    // 시작일이 현재일과 같음 (동일)
//    @Test
//    void createCancel4() throws Exception {
//        CancelDto cancelDto = new CancelDto();
//
//        cancelDto.setCancelId(2L);
//        Long reqId = 1L;
//
//        cancelService.createCancel(cancelDto, reqId);
//    }


//   @Test
//    void createCancelTest() throws Exception {
//        CancelDto cancelDto = new CancelDto();
//        cancelDto.setCancelId(1L);
//        cancelDto.setCancelStatus("접수");
//
//       SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2023-04-29");
//        cancelDto.setCancelReqDate(testDate);
//
//        Long reqId = 1L;
//
//        cancelService.createCancel(cancelDto, reqId);
//   }


//    @Test
//    void createCancel() {
//        CancelDto dto = new CancelDto();
////        VcReqDto req = vcReqMapper.getVcReq(1L);
////        dto.setVcReqDto(req);
//
//        dto.setReqComm("일정 변경 test");
//
//        cancelService.createCancel(dto, 1L);
//    }
}