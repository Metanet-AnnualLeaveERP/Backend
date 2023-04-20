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
    void cancelListTest() throws Exception {
        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);
        criteria.setKeyword("테스트");

        UserDto userDto = new UserDto();
        userDto.setUserId(17L);

        cancelService.getCancelList(criteria, userDto.getUserId());
    }

   @Test
    void cancelDetailTest() throws Exception {
        Long cancelId = 10L;
        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        cancelService.getCancel(cancelId, userDto);
   }

   @Test
    void cancelListByMgrTest() throws Exception {
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