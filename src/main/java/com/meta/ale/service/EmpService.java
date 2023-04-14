package com.meta.ale.service;

import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import java.util.List;

public interface EmpService {

    /*1년이상 근무한 사원들 조회*/
    public List<EmpDto> findEmpOverOneYr() throws Exception;

    /*1년 근무한 사원들 조회*/
    public List<EmpDto> findEmpOneYr() throws Exception;

    /* 1년 미만 근무한 사원들 조회*/
    public List<EmpDto> findEmpUnderOneYr() throws Exception;

    /* 퇴사자 2년 이상일 경우 삭제 처리*/
    public void deleteEmpOverTwoYrLeaveDate() throws Exception;

    /* 회원 등록 */
    public void register(UserDto userDto, EmpDto empDto) throws Exception;

    /* UserId로 사원 조회 */
    public EmpDto findEmpByUserId(Long userId);

}
