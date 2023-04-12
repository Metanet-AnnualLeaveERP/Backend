package com.meta.ale.service;

import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;

public interface EmpService {
    public boolean register(UserDto userDto, EmpDto empDto) throws Exception;

    public boolean modifyEmp(EmpDto empDto) throws Exception;
}
