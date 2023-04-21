package com.meta.ale.service;

import com.meta.ale.domain.CpHolidayDto;
import com.meta.ale.domain.Criteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class CpHolidayServiceTest {

    @Autowired
    private CpHolidayService cpHolidayService;

    @Test
    void getCpHolidayListTest() throws Exception {
        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);
        criteria.setKeyword("테스트");

        cpHolidayService.getCpHolidayList(criteria);
    }

    @Test
    void getCpHolidayInfoTest() throws Exception {
        Long holidayId = 1L;
        cpHolidayService.getCpHolidayInfo(holidayId);
    }

    // db에 있는거 삭제해야함
    @Test
    void removeCpHolidayTest() throws Exception {
        Long holidayId = 80L;
        cpHolidayService.removeCpHoliday(holidayId);
    }

    @Test
    void removeCpHolidayTest2() throws Exception {
        Long holidayId = 100L;
        cpHolidayService.removeCpHoliday(holidayId);
    }

    @Test
    void addCpHoliday() throws Exception {
        CpHolidayDto cpHolidayDto = new CpHolidayDto();
        cpHolidayDto.setName(null);
        cpHolidayService.addCpHoliday(cpHolidayDto);
    }

    @Test
    void addCpHoliday2() throws Exception {
        CpHolidayDto cpHolidayDto = new CpHolidayDto();

        cpHolidayDto.setName("");
        cpHolidayService.addCpHoliday(cpHolidayDto);
    }

    @Test
    void addCpHoliday3() throws Exception {

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2022-03-20");

        CpHolidayDto cpHolidayDto = new CpHolidayDto();

        cpHolidayDto.setName("테스트");
        cpHolidayDto.setHoliday(testDate);

        cpHolidayDto.setDescription("테스트입니다");

        cpHolidayService.addCpHoliday(cpHolidayDto);

    }

    @Test
    void modifyCpHolidayTest() throws Exception{

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2022-03-20");

        CpHolidayDto cpHolidayDto = new CpHolidayDto();
        cpHolidayDto.setHolidayId(2L);
        cpHolidayDto.setName("테스트");
        cpHolidayDto.setHoliday(testDate);

        cpHolidayDto.setDescription("테스트입니다");

        cpHolidayService.modifyCpHoliday(cpHolidayDto);
    }

    // db에 없는 row
    @Test
    void modifyCpHolidayTest2() throws Exception{

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = date.parse("2022-03-20");

        CpHolidayDto cpHolidayDto = new CpHolidayDto();
        cpHolidayDto.setHolidayId(20L);
        cpHolidayDto.setName("테스트");
        cpHolidayDto.setHoliday(testDate);

        cpHolidayDto.setDescription("테스트입니다");

        cpHolidayService.modifyCpHoliday(cpHolidayDto);
    }

}
