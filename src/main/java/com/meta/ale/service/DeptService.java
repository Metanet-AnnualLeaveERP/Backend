package com.meta.ale.service;

import com.meta.ale.domain.DeptDto;

public interface DeptService {
    public DeptDto getByDeptName(String deptName);
}
