package com.meta.ale.mapper;

import com.meta.ale.domain.CancelDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.VcReqDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CancelMapperTest {

    @Autowired
    CancelMapper cancelMapper;

    @Autowired
    VcReqMapper vcReqMapper;

    @Test
    void getCancelList() {
        // Mapper에 들어갈 파라미터 map으로 변환
        HashMap<String, Object> vo = new HashMap<String, Object>();
        Criteria cri = new Criteria();

        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("userId", 1L);

        List<CancelDto> list = cancelMapper.getCancelList(vo);
        list.forEach(item -> System.out.println("휴가취소) " + item));
    }

    @Test
    void getCancel() {
        CancelDto dto = cancelMapper.getCancel(1L);
        System.out.println(dto.toString());
    }

    @Test
    void insertCancel() {
        VcReqDto req = vcReqMapper.getVcReq(1L);

        CancelDto dto = new CancelDto();
        dto.setStatus("취소요청");
        dto.setReqComm("일정 변경");
        dto.setVcReqDto(req);
    }
}