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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@SpringBootTest
public class GrantedVcServiceTest {

    @Autowired
    private GrantedVcService grantedVcService;

    @Test
    void getListGrantedVc(){
        Criteria criteria = new Criteria();
        Map<String, Object> map = grantedVcService.getListGrantedVc(criteria);
        for (Map.Entry<String, Object> entrySet : map.entrySet()) {
            System.out.println(entrySet.getKey() + " : " + entrySet.getValue());
        }
    }

    @Test
    void getGrantedVcTest(){
        Long vcId = 22L;
        grantedVcService.getGrantedVc(vcId);
    }

    @Test
    void updateAnnualCntTest() throws Exception {
        GrantedVcDto grantedVcDto = new GrantedVcDto();

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2023-04-29");
        Date testDate2 = date.parse("2023-05-30");

        grantedVcDto.setVcId(1L);
        grantedVcDto.setGrantedDate(testDate);
        grantedVcDto.setExpiredDate(testDate2);
        grantedVcDto.setVcDays(50L);
        grantedVcDto.setRemainDays(30.0);

        grantedVcService.updateAnnualCnt(grantedVcDto);
    }

    @Test
    void updateAnnualCntTest2() throws Exception {
        GrantedVcDto grantedVcDto = new GrantedVcDto();

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2023-04-29");
        Date testDate2 = date.parse("2023-05-30");

        grantedVcDto.setVcId(1000L);
        grantedVcDto.setGrantedDate(testDate);
        grantedVcDto.setExpiredDate(testDate2);
        grantedVcDto.setVcDays(50L);
        grantedVcDto.setRemainDays(30.0);

        grantedVcService.updateAnnualCnt(grantedVcDto);
    }


    // if (gvDto == null)
//    @Test
//    void deleteGrantedVcTest() throws Exception {
//        Long vcId = 4L;
//        grantedVcService.deleteGrantedVc(vcId);
//    }
//
//    // 삭제됨 db확인하고 해야함. 테스트 완료
//    @Test
//    void deleteGrantedVc2Test() throws Exception {
//        Long vcId = 2L;
//        grantedVcService.deleteGrantedVc(vcId);
//    }

    @Test
    void insertGrantedVcTest() throws Exception {
        GrantedVcDto grantedVcDto = new GrantedVcDto();

        VcTypeDto vcTypeDto = new VcTypeDto();
        vcTypeDto.setTypeId(2L);

        EmpDto empDto = new EmpDto();
        empDto.setEmpId(43L);


        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2023-04-29");
        Date testDate2 = date.parse("2023-05-30");

        grantedVcDto.setGrantedDate(testDate);
        grantedVcDto.setExpiredDate(testDate2);
        grantedVcDto.setVcDays(50L);
        grantedVcDto.setRemainDays(30.0);
        grantedVcDto.setVcTypeDto(vcTypeDto);
        grantedVcDto.setEmpDto(empDto);

        grantedVcService.insertGrantedVc(grantedVcDto);

    }

//    @Test
//    void insertGrantedVcTest2() throws Exception {
//        GrantedVcDto grantedVcDto = new GrantedVcDto();
//
//        VcTypeDto vcTypeDto = new VcTypeDto();
//        vcTypeDto.setTypeId(2L);
//
//        EmpDto empDto = new EmpDto();
//        empDto.setEmpId(43L);
//
//
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date testDate = date.parse("2023-04-29");
//        Date testDate2 = date.parse("2023-05-30");
//
//        grantedVcDto.setGrantedDate(testDate);
//        grantedVcDto.setVcDays(50L);
//        grantedVcDto.setRemainDays(30.0);
//        grantedVcDto.setVcTypeDto(vcTypeDto);
//        grantedVcDto.setEmpDto(empDto);
//
//        System.out.println(grantedVcService.insertGrantedVc(grantedVcDto));
//
//    }

    @Test
    void insertAnnualByEmpOverOneYrTest() throws Exception{
        grantedVcService.insertAnnualByEmpOverOneYr();
    }

    @Test
    void findPromoteAnnualLeaveTest() throws Exception {
        grantedVcService.findPromoteAnnualLeave();
    }

    @Test
    void getAnnualLeaveByEmpIdTest() throws Exception {
        Long empId = 41L;
        grantedVcService.getAnnualLeaveByEmpId(empId);
    }


//    @Test
//    void deleteGrantedVcTest(){
//        Long vcId = 28L;
//        boolean result = gvService.deleteGrantedVc(vcId);
//        //System.out.println("[RESULT]" + result);
//    }

//    @Test
//    void insertGrantedVcTest(){
//        GrantedVcDto grantedVcDto = new GrantedVcDto();
//        grantedVcDto.setGrantedDate(new Date());
//        grantedVcDto.setExpiredDate(new Date());
//        grantedVcDto.setRemainDays(17.0);
//        grantedVcDto.setVcDays(17L);
//
//        VcTypeDto typeDto = new VcTypeDto();
//        typeDto.setTypeId(1L);
//        grantedVcDto.setVcTypeDto(typeDto);
//
//        EmpDto empDto = new EmpDto();
//        empDto.setEmpId(4L);
//        grantedVcDto.setEmpDto(empDto);
//
//        gvService.insertGrantedVc(grantedVcDto);
//    }

//    @Test
//    public void insertAnnual() throws Exception{
//        gvService.insertAnnualByEmpOverOneYr();
//    }

//    @Test
//    void deleteGrantedVcTest() throws Exception {
//        Long vcId = 1L;
//        gvService.deleteGrantedVc(vcId);
//    }

}
