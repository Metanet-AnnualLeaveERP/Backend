package com.meta.ale.service;

import com.meta.ale.domain.CpHolidayDto;
import com.meta.ale.domain.Criteria;

import java.util.Map;

public interface CpHolidayService {
    public Map<String, Object> getCpHolidayList(Criteria criteria) throws Exception;

    public CpHolidayDto getCpHolidayInfo(Long holidayId) throws Exception;

    public boolean addCpHoliday(CpHolidayDto cpHolidayDto) throws Exception;

    public boolean removeCpHoliday(Long holidayId) throws Exception;

    public boolean modifyCpHoliday(CpHolidayDto cpHolidayDto) throws Exception;
}
