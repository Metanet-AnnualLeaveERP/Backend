package com.meta.ale.mapper;

import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.VcReqDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VcReqMapperTest {

    @Autowired
    private VcReqMapper vcReqMapper;

    @Test
    void insertVcReq() {
        for (int i = 1; i <= 3; i++) {
            VcReqDto dto = new VcReqDto();

            // 시작일
            Date startDate = new Date();
            startDate.setDate(startDate.getDate() + i);
            dto.setStartDate(startDate);

            // 종료일
            Date endDate = new Date();
            endDate.setDate((dto.getStartDate().getDate()) + i);
            dto.setEndDate(endDate);

            dto.setVcType("연차");
            dto.setReqDays(2L);
            dto.setComments(null);
            dto.setStatus("자동승인");
            dto.setAprvDate(null);
            dto.setFilePath(null);

            // emp
            EmpDto emp = new EmpDto();
            emp.setEmpId(1L);
            dto.setEmpDto(emp);

            vcReqMapper.insertVcReq(dto);
        }
    }
}