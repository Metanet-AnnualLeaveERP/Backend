package com.meta.ale.service;

import com.meta.ale.domain.EmpDto;
import com.meta.ale.mapper.EmpMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService{

    private final EmpMapper empMapper;

    private final UserService userService;

    @Override
    public List<EmpDto> findEmpOverOneYr() {
        return empMapper.findEmpOverOneYrList();
    }

    @Override
    public List<EmpDto> findEmpOneYr() {

        return empMapper.findEmpOneYrList();
    }
    @Override
    public List<EmpDto> findEmpUnderOneYr() {
        return empMapper.findEmpUnderOneYrList();
    }

    @Override
    public void deleteEmpOverTwoYrLeaveDate() {

        empMapper.deleteEmpOverTwoYrLeaveDate();

    }

}
