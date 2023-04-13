package com.meta.ale.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmpServiceTests {

    @Autowired
    private EmpService empService;
    @Test
    public void empOverTwoYrLeaveDate(){
        empService.deleteEmpOverTwoYrLeaveDate();
    }
}
