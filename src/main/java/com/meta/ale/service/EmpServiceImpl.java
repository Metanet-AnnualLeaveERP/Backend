package com.meta.ale.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.meta.ale.domain.*;
import com.meta.ale.mapper.DeptMapper;
import com.meta.ale.mapper.EmpMapper;
import com.meta.ale.mapper.UserMapper;
import oracle.security.crypto.core.ECC;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EmpServiceImpl implements EmpService {

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
            if (deptMgrId != null) {
                return false;   // 팀장이 이미 있으면 팀장 가입 안됨
            }
            userDto.setRole("ROLE_MANAGER");
            if (userMapper.insertUser(userDto) == 0) {
                return false;
            }

            empDto.setUserDto(userDto);
            empDto.setDeptDto(deptDto);

            if (empMapper.insertEmp(empDto) == 0) {
                return false;
            }

            if (empMapper.selectDeptEmpCnt(deptDto.getDeptId()) > 0) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("deptId", deptDto.getDeptId());
                paramMap.put("mgrId", empDto.getEmpId());

                if (empMapper.updateEmpList(paramMap) == 0)
                    return false;
                else
                    return true;
            }
            return true;
        } else if (position.equals("사원")) {
            userDto.setRole("ROLE_EMP");
            if (userMapper.insertUser(userDto) == 0) {
                return false;
            }

            empDto.setUserDto(userDto);
            empDto.setDeptDto(deptDto);
            empDto.setMgrId(deptMgrId);
            if (empMapper.insertEmp(empDto) == 1) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
// register 테스트 해야함

    // modifyEmp 구현 해야함
    // 팀장이 부서를 바꾸면 전에 있던 직원들과 새로운 부서의 직원들의 mgrid 변경
    // 당연히 해당 부서에 팀장이 있는지 확인해야함

    // 직원이 부서를 바꾸면 부서와 mgrid 변경

    // 사원에서 팀장이 될 경우(그 전에 팀장 자리가 비워져 있어야함)
    // 직책과 role을 변경하고 mgrId를 null
    // 원래 부서에서 팀장이 될 경우 그 부서의 팀원들을 새로운 팀장의 empId로 mgrId설정
    // 새로운 부서로 간다면 원래 있던 부서의 팀원들의 mgrId를 비우고
    // 새로운 부서의 팀원들의 mgrId를 새로운 팀장의 empId로 바꿔
    @Override
    public boolean modifyEmp(EmpDto empDto) throws Exception {
        System.out.println(empDto.toString());
        DeptDto newDeptDto = deptMapper.selectByDeptName(empDto.getDeptDto().getDeptName());  // 변경하는 부서정보
        DeptDto originDeptDto = deptMapper.selectByempId(empDto.getEmpId());    // 기존의 부서 정보

        Long newDeptMgrId = empMapper.selectDeptMgr(newDeptDto.getDeptId()); // 변경하는 부서의 팀장아이디
        UserDto userDto = userMapper.selectByEmpId(empDto.getEmpId());
        String originPosition = empMapper.selectPositionByEmpId(empDto.getEmpId());

        String position = empDto.getPosition(); // 변경하는 직책
        if (position.equals("팀장")) {
            if (newDeptMgrId != null) {
                return false;   // 변경하려는 부서에 팀장이 이미 있으면 변경 불가
            }
            userDto.setRole("ROLE_MANAGER");
            if (userMapper.updateRole(userDto) == 0) {
                return false;
            }
            empDto.setUserDto(userDto);
            empDto.setDeptDto(newDeptDto);
            empDto.setMgrId(null);
            if (empMapper.updateEmp(empDto) == 0) {
                return false;
            }
            if (newDeptDto.getDeptId() == originDeptDto.getDeptId()) { // 새로운 부서가 기존 부서랑 같을 때
                if (empMapper.selectDeptEmpCnt(newDeptDto.getDeptId()) > 0) {
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("deptId", newDeptDto.getDeptId());
                    paramMap.put("mgrId", empDto.getEmpId());
                    if (empMapper.updateEmpList(paramMap) == 0)
                        return false;
                    else
                        return true;
                }
            } else {                // 새로운 부서로 옮길 때
                if (originPosition.equals(position)) { // 팀장 -> 팀장이면 기존 부서 사원들 mgrid 비우고 새로운 부서 사원들 mgrid update
                    if (empMapper.selectDeptEmpCnt(originDeptDto.getDeptId()) > 0) { // 기존 부서 사원들의 mgrId를 null
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("deptId", originDeptDto.getDeptId());
                        paramMap.put("mgrId", null);
                        if (empMapper.updateEmpList(paramMap) == 0)
                            return false;
                        else
                            return true;
                    }
                    if (empMapper.selectDeptEmpCnt(newDeptDto.getDeptId()) > 0) { // 새로운 부서 사원들의 mgrId를 바꿔줌
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("deptId", newDeptDto.getDeptId());
                        paramMap.put("mgrId", empDto.getEmpId());
                        if (empMapper.updateEmpList(paramMap) == 0)
                            return false;
                        else
                            return true;
                    }
                } else {    // 사원 -> 팀장이면 기존 부서 사원들 mgrid 놔두고 새로운 부서 사원들 mgrid update
                    if (empMapper.selectDeptEmpCnt(newDeptDto.getDeptId()) > 0) { // 새로운 부서 사원들의 mgrId를 바꿔줌
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("deptId", newDeptDto.getDeptId());
                        paramMap.put("mgrId", empDto.getEmpId());
                        if (empMapper.updateEmpList(paramMap) == 0)
                            return false;
                        else
                            return true;
                    }
                }
            }
            return true;
        } else if (position.equals("사원")) {
            userDto.setRole("ROLE_EMP");
            if (userMapper.updateRole(userDto) == 0) {
                return false;
            }
            empDto.setUserDto(userDto);
            empDto.setDeptDto(newDeptDto);
            empDto.setMgrId(newDeptMgrId);
            if (empMapper.updateEmp(empDto) == 0) {
                return false;
            } // 사원에서 사원이면 여기서 끝남 (부서, mgrId, )
            if (!originPosition.equals(position)) { // 팀장에서 사원 (사원에서 사원이 아닐 경우)
                // 부서가 바뀌었다면
                if (newDeptDto.getDeptId() == originDeptDto.getDeptId()) { // 새로운 부서가 기존 부서랑 같을 때
                    if (empMapper.selectDeptEmpCnt(newDeptDto.getDeptId()) > 0) { //부서의 사원들 mgrid를 비워줌
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("deptId", newDeptDto.getDeptId());
                        paramMap.put("mgrId", null);
                        if (empMapper.updateEmpList(paramMap) == 0)
                            return false;
                        else
                            return true;
                    }

                }else {                // 새로운 부서로 옮길 때
                    if (empMapper.selectDeptEmpCnt(originDeptDto.getDeptId()) > 0) { // 기존 부서 사원들의 mgrId를 null
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("deptId", originDeptDto.getDeptId());
                        paramMap.put("mgrId", null);
                        if (empMapper.updateEmpList(paramMap) == 0)
                            return false;
                        else
                            return true;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public EmpDto getEmpInfo(Long empId) throws Exception {
        return empMapper.selectEmpByEmpId(empId);
    }

    @Override
    @Transactional
    public Map<String, Object> getEmpList(Criteria criteria) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNum", criteria.getPageNum());
        paramMap.put("amount", criteria.getAmount());

        Map<String, Object> res = new HashMap<>();
        res.put("paging", new PagenationDTO(criteria, getEmpCnt()));
        res.put("empList", empMapper.getEmpList(paramMap));

        return  res;
    }

    @Override
    public boolean modifyInfo(UserDto userDto, EmpDto empDto) throws Exception {

        if (!userDto.getPwd().equals("")) {
            userDto.setPwd(passwordEncoder.encode(userDto.getPwd()));
            if (userMapper.updatePwd(userDto) <= 0) {
                return false;
            }
        }

        if (empMapper.updateEmpInfo(empDto) > 0) {
            return true;
        }
        return false;
    }

    private Integer getEmpCnt() throws Exception {
        return empMapper.selectEmpListCnt();
    }


    // 이직을 했어
    // enabled 0이 됐어
    // 그럼? 해당 팀원들의 mgrid를 비워야지

}
