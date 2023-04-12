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
    public Long insertEmp(EmpDto empDto) throws Exception;
    public Long selectDeptMgr(Long deptId) throws Exception;
    public Long updateEmp(EmpDto empDto) throws Exception;
    public UserDto selectByEmpId(Long empId) throws Exception;
    public List<EmpDto> selectListByDeptId(Long deptId) throws Exception;
    public Long updateEmpList(Map<String, Object> paramMap) throws Exception;
}
