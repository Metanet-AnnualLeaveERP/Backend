//package com.meta.ale.mapper;
//
//import com.meta.ale.domain.AnpDocDto;
//import com.meta.ale.domain.Criteria;
//import com.meta.ale.domain.EmpDto;
//import com.meta.ale.domain.VcReqDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class AnpDocMapperTest {
//    @Autowired
//    AnpDocMapper mapper;
//
//    @Test
//    void insertAnpDocTest(){
//        AnpDocDto dto = new AnpDocDto();
//        EmpDto empDto = new EmpDto();
//        empDto.setEmpId(2L);
//        dto.setTotalAnv(15);
//        dto.setUsedAnv(5);
//        dto.setRemainAnv(10);
//        dto.setOccurDate(new Date());
//        dto.setAnvOccurDate(new Date());
//        dto.setEmpDto(empDto);
//        mapper.insertAnpDocMapper(dto);
//    }
//
//    @Test
//    void getListAnpDoc(){
//        HashMap<String, Object> map = new HashMap<>();
//        Criteria cri = new Criteria();
//
//        map.put("pageNum", cri.getPageNum());
//        map.put("amount", cri.getAmount());
//
//        List<AnpDocDto> list = mapper.getListAnpDoc(map);
//        for (AnpDocDto anpDocDto : list) {
//            System.out.println(anpDocDto);
//        }
//    }
//
//    @Test
//    void deleteAnpDtoTest(){
//        AnpDocDto dto = new AnpDocDto();
//        mapper.deleteAnpDoc(10L);
//    }
//
//    @Test
//    void getAnpDoc(){
//        mapper.getAnpDoc(9L);
//    }
//}