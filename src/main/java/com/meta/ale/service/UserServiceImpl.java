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
    public boolean checkEmail(String email) throws Exception {
        if (empMapper.selectDuplicatedEmail(email) > 0) {
            return true;
        }
        return false;
    }

//    private UserDetails createUserDetails(UserDto userDto) {
//        return User.builder()
//                .username(userDto.getEmpNum())
//                .password(passwordEncoder.encode(userDto.getPassword()))
//                .roles(userDto.getRole())
//                .build();
//    }


//    //1. 로그인 요청으로 들어온 memberId, password를 기반으로 Authentication 객체를 생성
//    //2. authenticate() 메서드를 통해 요청된 user에 대한 검증이 진행
//    //3. 검증이 정상적으로 통과되었다면 인증된 Authentication 객체를 기반으로 JWT 토큰을 생성
//    @Override
//    public TokenDto login(String empNum, String pwd) {
//        // 1. Login ID/PW를 기반으로 Authentication 객체 생성
//        // 이 때 authentication은 인증 여부를 확인하는 authenticated 값이 false
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(empNum, pwd);
//
//        // 2. 실제 검증(사용자 비밀번호 체크)이 이루어지는 부분
//        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
//
//        return tokenDto;
//    }
}
