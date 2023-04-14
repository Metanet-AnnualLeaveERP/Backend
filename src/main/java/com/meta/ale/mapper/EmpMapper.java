package com.meta.ale.mapper;

import com.meta.ale.domain.EmpDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpMapper {

    //근속 년수가 1년 초과인 사람 조회
    public List<EmpDto> findEmpOverOneYrList();

    //근속 년수가 1년 미만인 사람들을 조회
    public List<EmpDto> findEmpUnderOneYrList();

    //근속 년수가 1년이 된 사람들 조회
    public List<EmpDto> findEmpOneYrList();

    public List<EmpDto> findEmpOverTwoYrLeaveDate();

    public void insertEmp(EmpDto empDto);

    /*UserId로 사원 조회 */
    public EmpDto findEmpByUserId(Long userId);
}
