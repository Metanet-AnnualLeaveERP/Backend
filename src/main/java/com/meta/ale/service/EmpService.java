package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;

import java.util.Map;

public interface EmpService {
    public boolean register(UserDto userDto, EmpDto empDto) throws Exception;

    public boolean modifyEmp(EmpDto empDto) throws Exception;

    public EmpDto getEmpInfo(Long empId) throws Exception;

    public Map<String, Object> getEmpList(Criteria criteria) throws Exception;

    public boolean modifyInfo(UserDto userDto, EmpDto empDto) throws Exception;
}
