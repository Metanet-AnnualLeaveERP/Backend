package com.meta.ale.service;

import com.meta.ale.domain.AnpDocDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UsePlanDto;
import com.meta.ale.domain.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class UserPlanServiceTest {

    @Autowired
    private UsePlanService usePlanService;

    @Test
    void getUsePlanListTest() throws Exception {
        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);
        criteria.setKeyword("전체");

        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        usePlanService.getUsePlanList(criteria, userDto);
    }

    @Test
    void getUsePlanByPlanIdTest() throws Exception {
        Long planId = 1L;
        usePlanService.getUsePlanByPlanId(planId);
    }

    @Test
    void modifyUsePlanTest() throws Exception {
        UsePlanDto usePlanDto = new UsePlanDto();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2023-04-20");
        Date testDate2 = date.parse("2023-05-20");


        usePlanDto.setPlanId(1L);
        usePlanDto.setStartDate(testDate);
        usePlanDto.setEndDate(testDate2);
        usePlanDto.setUseDays(2);
        usePlanService.modifyUsePlan(usePlanDto);
    }

    @Test
    void modifyUsePlanTest2() throws Exception {
        UsePlanDto usePlanDto = new UsePlanDto();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2023-04-20");
        Date testDate2 = date.parse("2023-05-20");


        usePlanDto.setPlanId(1000L);
        usePlanDto.setStartDate(testDate);
        usePlanDto.setEndDate(testDate2);
        usePlanDto.setUseDays(2);
        usePlanService.modifyUsePlan(usePlanDto);
    }

    @Test
    void addUsePlanTest() throws Exception {
        UsePlanDto usePlanDto = new UsePlanDto();
        AnpDocDto anpDocDto = new AnpDocDto();

        anpDocDto.setDocId(1L);

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2023-04-20");
        Date testDate2 = date.parse("2023-05-20");


        usePlanDto.setStartDate(testDate);
        usePlanDto.setEndDate(testDate2);
        usePlanDto.setUseDays(2);
        usePlanDto.setAnpDocDto(anpDocDto);

        usePlanService.addUsePlan(usePlanDto);
    }

    @Test
    void addUsePlanListTest() throws Exception {
        UsePlanDto usePlanDto = new UsePlanDto();
        AnpDocDto anpDocDto = new AnpDocDto();

        anpDocDto.setDocId(1L);

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2023-04-20");
        Date testDate2 = date.parse("2023-05-20");


        usePlanDto.setStartDate(testDate);
        usePlanDto.setEndDate(testDate2);
        usePlanDto.setUseDays(2);
        usePlanDto.setAnpDocDto(anpDocDto);

        List<UsePlanDto> usePlanDtoList = new ArrayList<>();
        usePlanDtoList.add(usePlanDto);

        usePlanService.addUsePlanList(usePlanDtoList);
    }

    @Test
    void addUsePlanListTest2() throws Exception {
        List<UsePlanDto> usePlanDtoList = new ArrayList<>();
        usePlanService.addUsePlanList(usePlanDtoList);
    }

    @Test
    void selectUserPlanListByDocIdTest() throws Exception {
        Long docId = 1L;
        usePlanService.selectUserPlanListByDocId(docId);
    }

}
