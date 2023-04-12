package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.VcReqDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VcReqServiceTest {

    @Autowired
    VcReqService vcReqService;

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
        VcReqDto dto = vcReqService.getVcReq(4L);
        System.out.println("휴가 요청 하나)" + dto.toString());
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

        vcReqService.createVcReq(dto);
    }
}