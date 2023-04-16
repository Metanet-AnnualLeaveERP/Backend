package com.meta.ale.service;

import com.meta.ale.domain.CpHolidayDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.PagenationDTO;
import com.meta.ale.mapper.CpHolidayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CpHolidayServiceImpl implements CpHolidayService{

    private final CpHolidayMapper cpHolidayMapper;

    @Override
    @Transactional
    public Map<String, Object> getCpHolidayList(Criteria criteria) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNum", criteria.getPageNum());
        paramMap.put("amount", criteria.getAmount());
        paramMap.put("keyWord", criteria.getKeyword());

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("paging", new PagenationDTO(criteria, getCpHolidayCnt()));
        resMap.put("cpHolidayList", cpHolidayMapper.selectCpHolidayList(paramMap));

        return resMap;
    }

    @Override
    public CpHolidayDto getCpHolidayInfo(Long holidayId) throws Exception {
        return cpHolidayMapper.selectCpHolidayInfo(holidayId);
    }

    @Override
    public boolean addCpHoliday(CpHolidayDto cpHolidayDto) throws Exception {
        if (cpHolidayDto.getName() != null && !cpHolidayDto.getName().equals("")) {
            return cpHolidayMapper.insertCpHoliday(cpHolidayDto) > 0;
        }
        return false;
    }

    @Override
    public boolean removeCpHoliday(Long holidayId) throws Exception {
        return cpHolidayMapper.deleteCpHoliday(holidayId) > 0;
    }

    @Override
    public boolean modifyCpHoliday(CpHolidayDto cpHolidayDto) throws Exception {
        return cpHolidayMapper.updateCpHoliday(cpHolidayDto) > 0;
    }

    // 페이징용
    private Integer getCpHolidayCnt() throws Exception {
        return cpHolidayMapper.selectCpHolidayCnt();
    }
}
