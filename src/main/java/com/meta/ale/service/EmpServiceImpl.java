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

import java.util.*;

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
    public boolean register(UserDto userDto, EmpDto empDto) throws Exception {
        userDto.setPwd(passwordEncoder.encode(userDto.getPwd()));

        DeptDto deptDto = deptMapper.selectByDeptName(empDto.getDeptDto().getDeptName()); // 부서정보
        Long deptMgrId = empMapper.selectDeptMgr(deptDto.getDeptId()); // 팀장아이디
        String position = empDto.getPosition();

        if (position.equals("팀장")) {
            if (deptMgrId != null){
                return false;   // 팀장이 이미 있으면 팀장 가입 안됨
            }
            userDto.setRole("ROLE_MANAGER");
            userMapper.insertUser(userDto);

            empDto.setUserDto(userDto);
            empDto.setDeptDto(deptDto);

            empMapper.insertEmp(empDto);

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("deptId", deptDto.getDeptId());
            paramMap.put("mgrId", empDto.getEmpId());

            return (empMapper.updateEmpList(paramMap) != 0);

        } else {
            userDto.setRole("ROLE_EMP");
            userMapper.insertUser(userDto);

            empDto.setUserDto(userDto);
            empDto.setDeptDto(deptDto);

            empDto.setMgrId(deptMgrId);

            return empMapper.insertEmp(empDto) == 1;

        }
    }
// register 테스트 해야함

    // modifyEmp 구현 해야함
    // 팀장이 부서를 바꾸면 전에 있던 직원들과 새로운 부서의 직원들의 mgrid 변경
    // 당연히 해당 부서에 팀장이 있는지 확인해야함

    // 직원이 부서를 바꾸면 부서와 mgrid 변경

    // 직원에서 팀장이 될 경우(그 전에 팀장 자리가 비워져 있어야함)
    // 직책과 role을 변경하고 mgrId를 null
    // 원래 부서에서 팀장이 될 경우 그 부서의 팀원들을 새로운 팀장의 empId로 mgrId설정
    // 새로운 부서로 간다면 원래 있던 부서의 팀원들의 mgrId를 비우고
    // 새로운 부서의 팀원들의 mgrId를 새로운 팀장의 empId로 바꿔
    @Override
    public boolean modifyEmp(EmpDto empDto) throws Exception {
        DeptDto deptDto = deptMapper.selectByDeptName(empDto.getDeptDto().getDeptName());  // 부서정보
        Long deptMgrId = empMapper.selectDeptMgr(deptDto.getDeptId()); // 팀장아이디
        UserDto userDto = empMapper.selectByEmpId(empDto.getEmpId());

        String position = empDto.getPosition(); // 변경되서 넘어온 직책
        empDto.setDeptDto(deptDto);

        if (position.equals("팀장")) {
            if (deptMgrId != null){
                return false;   // 팀장이 이미 있으면 팀장 가입 안됨
            }
            userDto.setRole("ROLE_MANAGER");
        } else if (position.equals("사원")) {
            userDto.setRole("ROLE_EMP");
            empDto.setMgrId(deptMgrId);
        }




//        update emp
//        set
//        dept_id = #{deptId},
//        position = #{position}
//        where
//        emp_id = #{empId}
        return empMapper.updateEmp(empDto) == 1;
    }


    // 이직을 했어
    // enabled 0이 됐어
    // 그럼? 해당 팀원들의 mgrid를 비워야지
}
