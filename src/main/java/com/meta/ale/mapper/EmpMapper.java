package com.meta.ale.mapper;

import com.meta.ale.domain.EmpDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpMapper {

    //근속 년수가 1년 초과인 사람 조회
    List<EmpDto> findEmpOverOneYrList();

    //근속 년수가 1년 미만인 사람들을 조회
    List<EmpDto> findEmpUnderOneYrList();

    //근속 년수가 1년이 된 사람들 조회
    List<EmpDto> findEmpOneYrList();

    List<EmpDto> deleteEmpOverTwoYrLeaveDate();
}
