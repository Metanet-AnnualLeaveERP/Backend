package com.meta.ale.mapper;

import com.meta.ale.domain.EmpDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    public Integer insertEmp(EmpDto empDto) throws Exception;

    public Long selectDeptMgr(Long deptId) throws Exception;

    public Integer updateEmp(EmpDto empDto) throws Exception;

    public Integer updateEmpList(Map<String, Object> paramMap) throws Exception;

    public Long selectDeptEmpCnt(Long deptId) throws Exception;

    public String selectPositionByEmpId(Long empId) throws Exception;

    public EmpDto selectEmpByEmpId(Long empId) throws Exception;

    public Integer selectEmpListCnt() throws Exception;

    public List<EmpDto> selectEmpList(Map<String, Object> paramMap) throws Exception;

    public Integer updateEmpInfo(EmpDto empDto) throws Exception;

    public Integer selectHireOrder() throws Exception;

    public Integer updateLeaveDate(EmpDto empDto);

    public Integer selectDuplicatedEmail(String email);

    //근속 년수가 1년 초과인 사람 조회
    public List<EmpDto> findEmpOverOneYrList();

    //근속 년수가 1년 미만인 사람들을 조회
    public List<EmpDto> findEmpUnderOneYrList();

    //근속 년수가 1년이 된 사람들 조회
    public List<EmpDto> findEmpOneYrList();

    public List<EmpDto> findEmpOverTwoYrLeaveDate();

    /*UserId로 사원 조회 */
    public EmpDto findEmpByUserId(Long userId);

    /* MgrId로 상사 정보 조회 */
    public EmpDto getEmpByMgrId(Long mgrId);
}
