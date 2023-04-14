package com.meta.ale.mapper;

import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.domain.VcTypeTotalDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class VcTypeTotalMapperTest {

    @Autowired
    VcTypeTotalMapper mapper;

    @Test
    void insertVcTypeTotal(){
        VcTypeTotalDto dto = new VcTypeTotalDto();
        dto.setCnt(99L);

        VcTypeDto type = new VcTypeDto();
        type.setTypeId(1L);
        dto.setVcTypeDto(type);

        EmpDto emp = new EmpDto();
        emp.setEmpId(4L);
        dto.setEmpDto(emp);
        mapper.insertVcTypeTotal(dto);
    }

    @Test
    void updateVcTypeTotal(){
        VcTypeTotalDto dto = new VcTypeTotalDto();
        dto.setCnt(22L);

        VcTypeDto type = new VcTypeDto();
        type.setTypeId(1L);
        dto.setVcTypeDto(type);

        EmpDto emp = new EmpDto();
        emp.setEmpId(4L);
        dto.setEmpDto(emp);
        mapper.updateVcTypeTotal(dto);
    }

    @Test
    void plusVcTypeTotalTest(){VcTypeTotalDto dto = new VcTypeTotalDto();
        dto.setCnt(22L);

        VcTypeDto type = new VcTypeDto();
        type.setTypeId(1L);
        dto.setVcTypeDto(type);

        EmpDto emp = new EmpDto();
        emp.setEmpId(4L);
        dto.setEmpDto(emp);
        mapper.plusVcTypeTotal(dto);

    }
}