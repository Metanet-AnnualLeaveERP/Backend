package com.meta.ale.service;

import com.meta.ale.domain.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VcTypeTotalServiceTest {

    @Autowired
    private VcTypeTotalService vcTypeTotalService;

    @Test
    void findVcTypeTotalByEmpIdTest() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        vcTypeTotalService.findAllMyVacation(userDto);
    }

}
