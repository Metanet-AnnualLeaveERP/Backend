package com.meta.ale.service;

import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.mapper.VcTypeMapper;
import com.meta.ale.mapper.VcTypeTotalMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class VcTypeServiceTest {

    @Autowired
    VcTypeMapper typeMapper;

    @Autowired
    VcTypeTotalMapper totalMapper;

    @Autowired
    VcTypeService service;

    @Test
    void insertVcType(){
        VcTypeDto vcTypeDto = new VcTypeDto();

        vcTypeDto.setTypeName("테스트유형4");
        vcTypeDto.setDescription("테스트유형4입니다");
        vcTypeDto.setMaxGrantedDays(10L);
        vcTypeDto.setPto(0L);
        vcTypeDto.setExpiredDate(new Date());
        service.insertVcType(vcTypeDto);
    }

    @Test
    void updateVcType(){
        VcTypeDto vcTypeDto = new VcTypeDto();

        vcTypeDto.setTypeName("수정유형4");
        vcTypeDto.setDescription("제이유닛으로테스트");
        vcTypeDto.setPto(0L);
        vcTypeDto.setMaxGrantedDays(999L);
        vcTypeDto.setExpiredDate(new Date());
        vcTypeDto.setTypeId(36L);
        service.updateVcType(vcTypeDto);
    }
}
