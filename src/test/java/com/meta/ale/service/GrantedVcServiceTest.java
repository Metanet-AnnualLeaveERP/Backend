package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.GrantedVcDto;
import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.mapper.GrantedVcMapper;
import com.meta.ale.mapper.VcTypeTotalMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;

@SpringBootTest
public class GrantedVcServiceTest {

    @Autowired
    GrantedVcMapper gvMapper;

    @Autowired
    VcTypeTotalMapper totalMapper;

    @Autowired
    GrantedVcService gvService;

    @Test
    void getListGrantedVc(){
        Criteria criteria = new Criteria();
        Map<String, Object> map = gvService.getListGrantedVc(criteria);
        for (Map.Entry<String, Object> entrySet : map.entrySet()) {
            System.out.println(entrySet.getKey() + " : " + entrySet.getValue());
        }
    }

    @Test
    void getGrantedVcTest(){
        Long vcId = 22L;
        GrantedVcDto result = gvService.getGrantedVc(vcId);
        System.out.println(result);
    }

//    @Test
//    void deleteGrantedVcTest(){
//        Long vcId = 28L;
//        boolean result = gvService.deleteGrantedVc(vcId);
//        //System.out.println("[RESULT]" + result);
//    }

    @Test
    void insertGrantedVcTest(){
        GrantedVcDto grantedVcDto = new GrantedVcDto();
        grantedVcDto.setGrantedDate(new Date());
        grantedVcDto.setExpiredDate(new Date());
        grantedVcDto.setRemainDays(17L);
        grantedVcDto.setVcDays(17L);

        VcTypeDto typeDto = new VcTypeDto();
        typeDto.setTypeId(1L);
        grantedVcDto.setVcTypeDto(typeDto);

        EmpDto empDto = new EmpDto();
        empDto.setEmpId(4L);
        grantedVcDto.setEmpDto(empDto);

        gvService.insertGrantedVc(grantedVcDto);
    }
    @Test
    public void insertAnnual() throws Exception{
        gvService.insertAnnualByEmpOverOneYr();
    }

}
