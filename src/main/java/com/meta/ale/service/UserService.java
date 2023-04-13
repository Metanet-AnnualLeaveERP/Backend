package com.meta.ale.service;

import com.meta.ale.domain.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
//    public TokenDto login(String empNum, String pwd);

    public Optional<UserDto> getByEmpNum(String EmpNum) throws Exception;

}
