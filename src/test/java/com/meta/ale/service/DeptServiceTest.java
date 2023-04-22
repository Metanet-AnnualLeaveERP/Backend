package com.meta.ale.service;

import com.meta.ale.domain.DeptDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeptServiceTest {

    @Autowired
    private DeptService deptService;

    @Test
    void getListDeptTest() {
        deptService.getListDept();
    }

    @Test
    void getByDeptNameTest() {
        deptService.getByDeptName("개발팀");
    }

    @Test
    void getByDeptIdTest() {
        deptService.getByDeptId(1L);
    }

    @Test
    void updateDeptTest() {
        DeptDto deptDto = new DeptDto();

        deptDto.setDeptId(1L);
        deptDto.setDeptName("개발팀");
        deptDto.setVcTo(10L);
        deptService.updateDept(deptDto);
    }

    @Test
    void updateDeptTest2() throws Exception{
        DeptDto deptDto = new DeptDto();

        deptDto.setDeptId(20L);
        deptDto.setDeptName("개발팀");
        deptDto.setVcTo(10L);
        deptService.updateDept(deptDto);
    }

    @Test
    void insertDept() throws Exception {
        DeptDto deptDto = new DeptDto();

        deptDto.setDeptName("테스트2");
        deptDto.setVcTo(10L);
        deptService.insertDept(deptDto);
    }

}
