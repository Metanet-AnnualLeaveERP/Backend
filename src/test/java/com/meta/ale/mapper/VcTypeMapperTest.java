package com.meta.ale.mapper;

import com.meta.ale.domain.VcReqDto;
import com.meta.ale.domain.VcTypeDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
@Log4j2
class VcTypeMapperTest {

    @Autowired
    VcTypeMapper mapper;

    @Test
    void insertVcTypeTest() {
        VcTypeDto vc = new VcTypeDto();
        vc.setTypeName("테스트휴가유형3");
        vc.setDescription("테스트 휴가유형입니다3");
        vc.setPto(0L); // 유급
        vc.setMaxGrantedDate(new Date());
        vc.setStartEnableDate(new Date());
        vc.setExpiredDate(new Date());
        mapper.insertVcType(vc);
    }

    @Test
    void deleteVcTypeTest(){
        mapper.deleteVcType(2L);
    }

    @Test
    void updateVcTypeTest(){
        VcTypeDto vc = new VcTypeDto();
        vc.setTypeId(2L);
        vc.setTypeName("!수정! 테스트 휴가유형");
        vc.setDescription("!수정! 테스트 휴가유형입니다");
        vc.setPto(0L); // 무급
        vc.setMaxGrantedDate(new Date());
        vc.setStartEnableDate(new Date());
        vc.setExpiredDate(new Date());
        mapper.updateVcType(vc);
    }

    @Test
    void getListVcTypeTest(){
        List<VcTypeDto> list = mapper.getListVcType();
        list.forEach(item -> System.out.println("휴가요청) " + item));
    }
}