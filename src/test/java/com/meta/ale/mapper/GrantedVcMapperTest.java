package com.meta.ale.mapper;

import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.GrantedVcDto;
import com.meta.ale.domain.VcTypeDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class GrantedVcMapperTest {

    @Autowired
    GrantedVcMapper mapper;

    @Test
    void insertGrantedVcTest() throws Exception{
        GrantedVcDto dto = new GrantedVcDto();

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = null;
        try {
            testDate = date.parse("2023-04-29");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dto.setGrantedDate(testDate);
        dto.setExpiredDate(testDate);
        dto.setVcDays(10L);
        dto.setRemainDays(10L);

        VcTypeDto typeDto = new VcTypeDto();
        typeDto.setTypeId(1L);

        dto.setVcTypeDto(typeDto);

        EmpDto empDto = new EmpDto();
        empDto.setEmpId(1L);

        dto.setEmpDto(empDto);

        mapper.insertGrantedVc(dto);

    }

    @Test
    List<> getList

}