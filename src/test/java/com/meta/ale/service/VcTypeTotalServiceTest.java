package com.meta.ale.service;

import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcTypeTotalDto;
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

    //role admin
    @Test
    void findVcTypeTotalByEmpIdTest2() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_ADMIN");

        vcTypeTotalService.findAllMyVacation(userDto);
    }

    @Test
    void updateVcTypeTotalByTotalIdTest() throws Exception {
        VcTypeTotalDto vcTypeTotalDto = new VcTypeTotalDto();

        vcTypeTotalDto.setTotalId(1L);
        vcTypeTotalDto.setCnt(10L);

        vcTypeTotalService.updateVcTypeTotalByTotalId(vcTypeTotalDto);
    }

    @Test
    void updateVcTypeTotalByTotalIdTes2t() throws Exception {
        VcTypeTotalDto vcTypeTotalDto = new VcTypeTotalDto();

        vcTypeTotalDto.setTotalId(10000L);
        vcTypeTotalDto.setCnt(10L);

        vcTypeTotalService.updateVcTypeTotalByTotalId(vcTypeTotalDto);
    }

}
