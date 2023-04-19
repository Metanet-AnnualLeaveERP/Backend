package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;

import java.util.List;
import java.util.Map;

public interface EmpService {

    /* 사용자 계정 생성 */
    public boolean register(UserDto userDto, EmpDto empDto) throws Exception;

    /* 관리자 사용자 계정 수정 */
    /* 부서, 직책 수정 */
    public boolean modifyEmp(EmpDto empDto) throws Exception;

    /* 사원 상세 내역 조회 */
    public EmpDto getEmpInfo(Long empId) throws Exception;

    /* 사원 내역 조회 (paging) */
    public Map<String, Object> getEmpList(Criteria criteria) throws Exception;

    /* 팀원 사용자 계정 수정 */
    /*비밀번호, 개인 이메일 수정*/
    public boolean modifyInfo(UserDto userDto, EmpDto empDto) throws Exception;

    /*1년이상 근무한 사원들 조회*/
    public List<EmpDto> findEmpOverOneYr() throws Exception;

    /*1년 근무한 사원들 조회*/
    public List<EmpDto> findEmpOneYr() throws Exception;

    /* 1년 미만 근무한 사원들 조회*/
    public List<EmpDto> findEmpUnderOneYr() throws Exception;

    /* 퇴사자 2년 이상일 경우 삭제 처리*/
    public void deleteEmpOverTwoYrLeaveDate() throws Exception;

    /* UserId로 사원 조회 */
    public EmpDto findEmpByUserId(Long userId);

    /* MgrId로 상사 정보 조회 */
    public EmpDto getEmpByMgrId(Long mgrId);

    /* deptId로 emp count */
    public Long selectDeptEmpCnt(Long deptId) throws Exception;
}
