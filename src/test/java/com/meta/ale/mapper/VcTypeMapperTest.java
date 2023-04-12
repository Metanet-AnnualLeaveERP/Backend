package com.meta.ale.mapper;

import com.meta.ale.domain.VcTypeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class VcTypeMapperTest {

    @Autowired
    VcTypeMapper mapper;

    @Test
    void insertVcTypeTest() {
        VcTypeDto vc = new VcTypeDto();
        vc.setName("테스트 휴가유형");
        vc.setDescription("테스트 휴가유형입니다");
        vc.setPto(1L); // 유급
        vc.setMaxGrantedDate(new Date());
        vc.setStartEnableDate(new Date());
        vc.setEndEnableDate(new Date());
        mapper.insertVcType(vc);

    }

    @Test
    void deleteVcTypeTest(){
        mapper.deleteVcType(1L);
    }

    @Test
    void updateVcTypeTest(){
        VcTypeDto vc = new VcTypeDto();
        vc.setName(" !수정! 테스트 휴가유형");
        vc.setDescription("!수정! 테스트 휴가유형입니다");
        vc.setPto(0L); // 무급
        vc.setMaxGrantedDate(new Date());
        vc.setStartEnableDate(new Date());
        vc.setEndEnableDate(new Date());
        mapper.updateVcType(vc);
    }
}