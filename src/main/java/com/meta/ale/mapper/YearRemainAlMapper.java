package com.meta.ale.mapper;

import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.YearRemainAlDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface YearRemainAlMapper {

    public Integer selectYearRemainAlCnt(UserDto userDto) throws Exception;

    public List<YearRemainAlDto> selectYearRemainAlList (Map<String, Object> paramMap) throws Exception;

    public YearRemainAlDto selectYearRemainAlByAlId(long alId) throws Exception;

}
