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
        vc.setTypeName("mxdays휴가");
        vc.setDescription("mxdays휴가유형입니다");
        vc.setPto(0L); // 유급여부
        vc.setMaxGrantedDays(20L);
        vc.setStartEnableDate(new Date());
        mapper.insertVcType(vc);
    }

    @Test
    void deleteVcTypeTest(){
        mapper.deleteVcType(38L);
    }

    @Test
    void updateVcTypeTest(){
        VcTypeDto vc = new VcTypeDto();
        vc.setTypeId(36L);
        vc.setTypeName("수정");
        vc.setDescription("수정입니다");
        vc.setPto(0L); // 무급
        vc.setMaxGrantedDays(10L);
        vc.setStartEnableDate(new Date());
        mapper.updateVcType(vc);
    }

    @Test
    void getListVcTypeTest(){
        List<VcTypeDto> list = mapper.getListVcType();
        list.forEach(item -> System.out.println("휴가요청) " + item));
    }
}