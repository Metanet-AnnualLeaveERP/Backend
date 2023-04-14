package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcReqDto;
import com.meta.ale.mapper.VcReqMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;
import java.util.Optional;


@SpringBootTest
class VcReqServiceTest {

    @Autowired
    VcReqService vcReqService;

    @Autowired
    VcReqMapper vcReqMapper;

    @Autowired
    UserService userService;

    @Test
    void getVcReqList() {
        Criteria cri = new Criteria();
        Map<String, Object> map = vcReqService.getVcReqList(cri, 1L);

        for (Map.Entry<String, Object> entrySet : map.entrySet()) {
            System.out.println(entrySet.getKey() + " : " + entrySet.getValue());
        }
    }

    @Test
    void getVcReq() {
        VcReqDto dto = vcReqService.getVcReqCompared(5L, 1L);
        String result;
        result = dto == null ? "접근 실패" : dto.toString();
        System.out.println(result);
//        System.out.println("휴가 요청 하나)" + dto.toString());
    }

    @Test
    void createVcReq() {
        VcReqDto dto = new VcReqDto();

        // 시작일
        Date startDate = new Date();
        startDate.setDate(startDate.getDate() + 3);
        dto.setStartDate(startDate);

        // 종료일
        Date endDate = new Date();
        endDate.setDate((dto.getStartDate().getDate()) + 1);
        dto.setEndDate(endDate);

        dto.setVcType("연차");
        dto.setReqDays(2L);
        dto.setComments(null);
        dto.setStatus("자동승인");
        dto.setAprvDate(null);
        dto.setFilePath(null);

        // emp
        EmpDto emp = new EmpDto();
        emp.setEmpId(2L);
        dto.setEmpDto(emp);

//        vcReqService.createVcReq(dto);
    }

    @Test
    void updateVcReqStatus() {
        VcReqDto dto = vcReqMapper.getVcReq(2L);
        dto.setStatus("취소");
        System.out.println(vcReqService.updateVcReqStatus(dto));
    }

    @Test
    void approvalVcRequestList() throws Exception {
        Optional<UserDto> userDto = userService.getByEmpNum("admin");
        System.out.println(userDto.get());
        Criteria cri = new Criteria();
//        cri.setKeyword("취소");
        vcReqService.getApprovalVcRequestList(userDto.get(), cri);
    }
}