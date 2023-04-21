package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserPlanServiceTest {

    @Autowired
    private UsePlanService usePlanService;

    @Test
    void getUsePlanList() throws Exception {
        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);
        criteria.setKeyword("전체");

        UserDto userDto = new UserDto();
        userDto.setUserId(17L);
        userDto.setEmpNum("2222");
        userDto.setEnabled(1L);
        userDto.setRole("ROLE_EMP");

        usePlanService.getUsePlanList(criteria, userDto);
    }

    @Test
    void getUsePlan() throws Exception {
        Long planId = 1L;
        usePlanService.getUsePlanByPlanId(planId);
    }
}
