package com.meta.ale.service;


import com.meta.ale.domain.*;
import com.meta.ale.mapper.DeptMapper;
import com.meta.ale.mapper.EmpMapper;
import com.meta.ale.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {


    private final EmpMapper empMapper;

    private final UserMapper userMapper;

    private final DeptMapper deptMapper;

    private final PasswordEncoder passwordEncoder;

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
        List<EmpDto> empLeaveTwoYrList = empMapper.findEmpOverTwoYrLeaveDate();
        if (empLeaveTwoYrList.size() != 0) {
            for (EmpDto e : empLeaveTwoYrList) {

                userMapper.deleteUserByUserId(e.getUserDto());
            }
        }
    }

    // 사용자 계정 생성
    @Override
    @Transactional
    public boolean register(UserDto userDto, EmpDto empDto) throws Exception {
        userDto.setPwd(passwordEncoder.encode(userDto.getPwd()));

        DeptDto deptDto = deptMapper.selectByDeptName(empDto.getDeptDto().getDeptName()); // 부서정보
        Long deptMgrId = empMapper.selectDeptMgr(deptDto.getDeptId()); // 팀장아이디
        String position = empDto.getPosition();

        // 사번설정
        String empNum = createEmpNum(empDto, deptDto);
        userDto.setEmpNum(empNum);

        // 회사 이메일
        String cEmail = empNum + "gmail.com";
        empDto.setCEmail(cEmail);

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


    // modifyEmp 구현 해야함
    // 팀장이 부서를 바꾸면 전에 있던 직원들과 새로운 부서의 직원들의 mgrid 변경
    // 당연히 해당 부서에 팀장이 있는지 확인해야함

    // 직원이 부서를 바꾸면 부서와 mgrid 변경

    // 사원에서 팀장이 될 경우(그 전에 팀장 자리가 비워져 있어야함)
    // 직책과 role을 변경하고 mgrId를 null
    // 원래 부서에서 팀장이 될 경우 그 부서의 팀원들을 새로운 팀장의 empId로 mgrId설정
    // 새로운 부서로 간다면 원래 있던 부서의 팀원들의 mgrId를 비우고
    // 새로운 부서의 팀원들의 mgrId를 새로운 팀장의 empId로 바꿔

    // 관리자 사용자 계정 수정
    // 부서, 직책 수정
    @Override
    @Transactional
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

                } else {                // 새로운 부서로 옮길 때
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

    // 사원 상세 내역 조회
    @Override
    public EmpDto getEmpInfo(Long empId) throws Exception {
        return empMapper.selectEmpByEmpId(empId);
    }

    // 사원 내역 조회 (paging)
    @Override
    @Transactional
    public Map<String, Object> getEmpList(Criteria criteria) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNum", criteria.getPageNum());
        paramMap.put("amount", criteria.getAmount());
        paramMap.put("keyWord", criteria.getKeyword());

        Map<String, Object> res = new HashMap<>();
        res.put("paging", new PagenationDTO(criteria, getEmpCnt()));
        res.put("empList", empMapper.selectEmpList(paramMap));

        return res;
    }

    // 팀원 사용자 계정 수정
    // 비밀번호, 개인 이메일 수정
    @Override
    @Transactional
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

    // 페이징용
    private Integer getEmpCnt() throws Exception {
        return empMapper.selectEmpListCnt();
    }

    // 사번 생성
    private String createEmpNum(EmpDto empDto, DeptDto deptDto) throws Exception {

        String empNum = ""; // 사번

        String tempYear = empDto.getHireDate().toString();
        String hireYear = tempYear.substring(tempYear.length() - 2); // 연도 두자리

        String deptNum = Long.toString(deptDto.getDeptId()); // 부서번호 두자리
        if (deptNum.length() < 2) {
            deptNum = "0" + deptNum;
        }
        String hireOrder = Integer.toString(empMapper.selectHireOrder() + 1); // 입사 순서
        if (hireOrder.length() == 1) {
            hireOrder = "00" + hireOrder;
        } else if (hireOrder.length() == 2) {
            hireOrder = "0" + hireOrder;
        }
        empNum = hireYear + deptNum + hireOrder;

        return empNum;
    }

    @Override
    public EmpDto findEmpByUserId(Long userId) {
        return empMapper.findEmpByUserId(userId);
    }
}
