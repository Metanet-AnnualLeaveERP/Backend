package com.meta.ale.service;

import com.meta.ale.domain.DeptDto;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import com.meta.ale.mapper.DeptMapper;
import com.meta.ale.mapper.EmpMapper;
import com.meta.ale.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final EmpMapper empMapper;
    private final DeptMapper deptMapper;
    private final MailService mailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userMapper.selectByEmpNum(username)
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
        UserDto userDto = userMapper.selectByEmpNum(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
        return userDto;
    }

    @Override
    public Optional<UserDto> getByEmpNum(String EmpNum) {
        return userMapper.selectByEmpNum(EmpNum);
    }

    // 팀장이 enabled 0이 됐어
    // 그럼? 해당 팀원들의 mgrid를 비워야지
    @Override
    @Transactional
    public boolean modifyEnabled(UserDto userDto, EmpDto empDto) throws Exception {

        String position = empDto.getPosition();
        DeptDto deptInfoDto = deptMapper.selectByDeptName(empDto.getDeptDto().getDeptName());
        Long deptId = deptInfoDto.getDeptId();
        if (position.equals("팀원")) {
            if (userMapper.updateEnabled(userDto) == 0) {
                return false;
            }
        } else if (position.equals("팀장")) {
            if (userMapper.updateEnabled(userDto) == 0) {
                return false;
            }
            if (empMapper.selectDeptEmpCnt(deptId) > 0) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("deptId", deptId);
                paramMap.put("mgrId", null);
                if (empMapper.updateEmpList(paramMap) == 0) {
                    return false;
                }
            }
        } else {
            return false;
        }
        if (empMapper.updateLeaveDate(empDto) == 0) {
            return false;
        } else {
            return true;
        }
    }

    // 중복이메일 체크
    @Override
    public boolean checkEmail(String email, String empNum) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("empNum", empNum);
        EmpDto empDto = empMapper.selectDuplicatedEmail(map);
        BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
        if (empDto != null && empDto.getPEmail() != null) {
            UserDto user = empDto.getUserDto();
            String pwd = empDto.getUserDto().getUsername() + "" + empDto.getEmpId();
            String encodingPwd = bCryptPasswordEncoder.encode(pwd);

            user.setPwd(encodingPwd);
            System.out.println(user);
            userMapper.updatePwd(user);
            StringBuilder sb = new StringBuilder();
            sb.append("비밀번호 : ");
            sb.append(pwd + "입니다.\n");
            sb.append("로그인 후 패스워드를 변경해주세요.");
            mailService.sendToPEmail(empDto, "<메타넷>비밀번호 찾기", "비밀번호 찾기 ",
                    sb.toString());
            return true;
        }
        return false;
    }
}
