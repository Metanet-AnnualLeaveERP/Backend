package com.meta.ale.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GrantedVacataionServiceTests {

    @Autowired
    private GrantedVacationService grantedVacationService;

    @Test
    public void insertAnnual() throws Exception{
        grantedVacationService.insertAnnualByEmpOverOneYr();
    }

}
