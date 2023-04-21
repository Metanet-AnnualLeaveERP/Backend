package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void removeCpHoliday() throws Exception {
        Long holidayId = 1L;
        cpHolidayService.removeCpHoliday(holidayId);
    }


}
