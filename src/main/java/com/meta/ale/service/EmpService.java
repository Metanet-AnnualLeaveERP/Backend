package com.meta.ale.service;

import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;

public interface EmpService {
    public void register(UserDto userDto, EmpDto empDto) throws Exception;
}
