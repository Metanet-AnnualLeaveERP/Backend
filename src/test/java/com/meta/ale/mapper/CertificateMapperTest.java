//package com.meta.ale.mapper;
//
//import com.meta.ale.domain.CertificateDto;
//import com.meta.ale.domain.Criteria;
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
//class CertificateMapperTest {
//
//    @Autowired
//    private CertificateMapper certificateMapper;
//
//    @Autowired
//    private VcReqMapper vcReqMapper;
//
//    @Test
//    void getCertList() {
//        // Mapper에 들어갈 파라미터 map으로 변환
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        Criteria cri = new Criteria();
//
//        map.put("pageNum", cri.getPageNum());
//        map.put("amount", cri.getAmount());
//        map.put("userId", 2L);
//
//        List<CertificateDto> list = certificateMapper.getCertList(map);
//        list.forEach(item -> System.out.println("휴가요청) " + item));
//
//    }
//
//    @Test
//    void getCert() {
//        CertificateDto dto = certificateMapper.getCert(1L);
//        System.out.println(dto.toString());
//    }
//
//    @Test
//    void insertCert() {
//        CertificateDto dto = new CertificateDto();
//        dto.setPurpose("junit 테스트");
//        dto.setIssuedDate(new Date());
//        VcReqDto vcReqDto = vcReqMapper.getVcReq(30L);
//        dto.setVcReqDto(vcReqDto);
//        certificateMapper.insertCert(dto);
//    }
//
//    @Test
//    void getCertCount() {
//        Long cnt = certificateMapper.getCertCount(2L);
//        System.out.println(cnt);
//    }
//}