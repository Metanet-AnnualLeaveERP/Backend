package com.meta.ale.service;

import com.meta.ale.domain.EmpDto;

import java.util.List;

public interface EmpService {

    //1년이상 근무한 사원들 조회
    List<EmpDto> findEmpOverOneYr();

    //1년 근무한 사원들 조회
    List<EmpDto> findEmpOneYr();

    // 1년 미만 근무한 사원들 조회
    List<EmpDto> findEmpUnderOneYr();

    void deleteEmpOverTwoYrLeaveDate();
}
