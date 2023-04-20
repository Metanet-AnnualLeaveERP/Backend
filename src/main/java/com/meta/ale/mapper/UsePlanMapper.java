package com.meta.ale.mapper;

import com.meta.ale.domain.UsePlanDto;
import com.meta.ale.domain.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UsePlanMapper {
    public List<UsePlanDto> selectUsePlanList(Map<String, Object> paramMap) throws Exception;

    public Integer selectUsePlanCnt(UserDto userDto) throws Exception;

    public UsePlanDto selectUsePlanByPlanId(Long planId) throws Exception;

    public Integer updateUsePlan(UsePlanDto usePlanDto) throws Exception;

    public Integer insertUsePlan(UsePlanDto usePlanDto) throws Exception;

    //Integer insertUsePlanList(List<UsePlanDto> usePlanDtoList) throws Exception;
}
