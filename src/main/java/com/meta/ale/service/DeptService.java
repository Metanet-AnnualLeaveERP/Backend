package com.meta.ale.service;

import com.meta.ale.domain.DeptDto;

import java.util.List;

public interface DeptService {
    public DeptDto getByDeptName(String deptName);

    List<DeptDto> getListDept();

    boolean updateDept(DeptDto deptDto);

    void insertDept(DeptDto deptDto);

}
