package com.meta.ale.service;

import com.meta.ale.domain.DeptDto;
import com.meta.ale.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl implements DeptService{

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public DeptDto getByDeptName(String deptName) {
        return deptMapper.selectByDeptName(deptName);
    }
}
