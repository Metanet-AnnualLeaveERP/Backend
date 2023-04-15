package com.meta.ale.service;

import com.meta.ale.domain.DeptDto;
import com.meta.ale.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService{

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public DeptDto getByDeptName(String deptName) {
        return deptMapper.selectByDeptName(deptName);
    }

    @Override
    public List<DeptDto> getListDept() {
        return deptMapper.getListDept();
    }

    @Override
    public boolean updateDept(DeptDto deptDto) {
        int result = deptMapper.updateDept(deptDto);
        return result != 0;
    }

    /* 부서 생성 */
    @Override
    public void insertDept(DeptDto deptDto) {
        deptMapper.insertDept(deptDto);
    }



}
