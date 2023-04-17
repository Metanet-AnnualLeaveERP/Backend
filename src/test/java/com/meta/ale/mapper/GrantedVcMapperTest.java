package com.meta.ale.mapper;

import com.meta.ale.domain.Criteria;
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
import java.util.HashMap;
import java.util.List;


@SpringBootTest
@Log4j2
class GrantedVcMapperTest {

    @Autowired
    GrantedVcMapper mapper;

//    @Test
//    void insertGrantedVcTest() throws Exception{
//        GrantedVcDto dto = new GrantedVcDto();
//
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = null;
//        try {
//            testDate = date.parse("2023-04-29");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        dto.setGrantedDate(testDate);
//        dto.setExpiredDate(testDate);
//        dto.setVcDays(4L);
//        dto.setRemainDays(4L);
//
//        VcTypeDto typeDto = new VcTypeDto();
//        typeDto.setTypeId(1L);
//
//        dto.setVcTypeDto(typeDto);
//
//        EmpDto empDto = new EmpDto();
//        empDto.setEmpId(999L);
//
//        dto.setEmpDto(empDto);
//
//        int result = mapper.insertGrantedVc(dto);
//        System.out.println("[][][][][][][][]"+result);
//    }

    @Test
    void getListGrantedVcTest(){
        HashMap<String, Object> map = new HashMap<>();
        Criteria criteria = new Criteria();

        map.put("pageNum", criteria.getPageNum());
        map.put("amount", criteria.getAmount());
        map.put("userId", 2L);

        List<GrantedVcDto> list = mapper.getListGrantedVc(map);
        list.forEach(item -> System.out.println("[[휴가임의부여내역]]"+ item));
    }

    @Test
    void updateGrantedVcTest(){
        GrantedVcDto gvDto = mapper.getGrantedVc(2L);
        gvDto.setVcDays(99L);
        gvDto.setRemainDays(99L);
        System.out.println(mapper.updateGrantedVc(gvDto));
        System.out.println(gvDto);
    }

    @Test
    void getGrantedVcTest(){
        GrantedVcDto dto = new GrantedVcDto();
        dto = mapper.getGrantedVc(14L);
        System.out.println(dto);
    }

    @Test
    void deleteGrantedVcTest(){
        mapper.deleteGrantedVc(1L);
    }

    @Test
    void getVcReqCount() {
        System.out.println(mapper.getGrantedVcCount());
    }

    @Test
    public void getListAnnualLeave(){
        HashMap<String, Object> map = new HashMap<>();
        Criteria criteria = new Criteria();

        map.put("pageNum", criteria.getPageNum());
        map.put("amount", criteria.getAmount());

        List<GrantedVcDto> list = mapper.getListAnnualLeave(map);
        list.forEach(item -> System.out.println("[[연차휴가내역]]"+ item));
    }
}
