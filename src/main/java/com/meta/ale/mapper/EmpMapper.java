package com.meta.ale.mapper;

import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public List<EmpDto> getEmpList(Map<String, Object> paramMap) throws Exception;
    public Integer updateEmpInfo(EmpDto empDto) throws Exception;
}
