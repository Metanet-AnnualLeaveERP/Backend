package com.meta.ale.service;

import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import java.util.List;

public interface EmpService {

    //1년이상 근무한 사원들 조회
    public List<EmpDto> findEmpOverOneYr() throws Exception;

    //1년 근무한 사원들 조회
    public List<EmpDto> findEmpOneYr() throws Exception;

    // 1년 미만 근무한 사원들 조회
    public List<EmpDto> findEmpUnderOneYr() throws Exception;

    public void deleteEmpOverTwoYrLeaveDate() throws Exception;

    public void register(UserDto userDto, EmpDto empDto) throws Exception;
}
