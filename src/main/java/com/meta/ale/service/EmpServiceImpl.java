package com.meta.ale.service;

import com.meta.ale.domain.DeptDto;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import com.meta.ale.mapper.DeptMapper;
import com.meta.ale.mapper.EmpMapper;
import com.meta.ale.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpServiceImpl implements EmpService{

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(UserDto userDto, EmpDto empDto) throws Exception {
        System.out.println("123123123123123123123123123123" + userDto.getEmpNum() + userDto.getRole() + " asdf  " + userDto.getPwd());
        userDto.setPwd(passwordEncoder.encode(userDto.getPwd()));
        userMapper.insertUser(userDto);

        DeptDto deptDto = deptMapper.selectByDeptName(empDto.getDeptDto().getDeptName());

        empDto.setUserDto(userDto);

        empDto.setDeptDto(deptDto);

        System.out.println(empDto.toString());
        empMapper.insertEmp(empDto);

    }
}
