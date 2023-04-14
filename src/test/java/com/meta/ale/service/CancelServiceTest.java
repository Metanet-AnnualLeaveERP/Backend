package com.meta.ale.service;

import com.meta.ale.domain.CancelDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.VcReqDto;
import com.meta.ale.mapper.VcReqMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CancelServiceTest {

    @Autowired
    CancelService cancelService;

    @Autowired
    VcReqMapper vcReqMapper;

    @Test
    void getCancelList() {
        Criteria cri = new Criteria();
        Map<String, Object> map = cancelService.getCancelList(cri, 1L);

        for (Map.Entry<String, Object> entrySet : map.entrySet()) {
            System.out.println(entrySet.getKey() + " : " + entrySet.getValue());
        }
    }

    @Test
    void getCancel() {
        CancelDto dto = cancelService.getCancel(1L, 5L);
        String result;
        result = dto == null ? "접근 실패" : dto.toString();
        System.out.println(result);
    }

    @Test
    void createCancel() {
        CancelDto dto = new CancelDto();
//        VcReqDto req = vcReqMapper.getVcReq(1L);
//        dto.setVcReqDto(req);

        dto.setReqComm("일정 변경 test");

        cancelService.createCancel(dto, 1L);
    }
}