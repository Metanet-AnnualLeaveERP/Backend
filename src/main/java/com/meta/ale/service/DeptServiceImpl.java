package com.meta.ale.service;

import com.meta.ale.domain.DeptDto;
import com.meta.ale.mapper.DeptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService{

    private final DeptMapper deptMapper;

    @Override
    public DeptDto getByDeptName(String deptName) {
        return deptMapper.selectByDeptName(deptName);
    }

    /*deptId 로 부서 정보 조회*/
    @Override
    public DeptDto getByDeptId(Long deptId) {
        return deptMapper.getByDeptId(deptId);
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
