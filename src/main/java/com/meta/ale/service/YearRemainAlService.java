package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.YearRemainAlDto;

import java.util.Map;

public interface YearRemainAlService {

    public Map<String, Object> getYearRemainAlList(Criteria criteria, UserDto userDto) throws Exception;

    public YearRemainAlDto getYearRemainAlInfo(long alId) throws Exception;
}
