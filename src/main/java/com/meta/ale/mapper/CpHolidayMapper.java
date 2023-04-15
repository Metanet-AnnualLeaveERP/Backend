package com.meta.ale.mapper;

import com.meta.ale.domain.CpHolidayDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CpHolidayMapper {

    public Integer selectCpHolidayCnt() throws Exception;

    public List<CpHolidayDto> selectCpHolidayList(Map<String, Object> paramMap) throws Exception;

    public CpHolidayDto selectCpHolidayInfo(Long holidayId) throws Exception;

    public Integer insertCpHoliday(CpHolidayDto cpHolidayDto) throws Exception;

    public Integer deleteCpHoliday(Long holidayId) throws Exception;

    public Integer updateCpHoliday(CpHolidayDto cpHolidayDto) throws Exception;

}
