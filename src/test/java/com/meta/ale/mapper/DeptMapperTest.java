package com.meta.ale.mapper;

import com.meta.ale.domain.DeptDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DeptMapperTest {

    @Autowired
    DeptMapper deptMapper;

    @Test
    void getListDept(){
        List<DeptDto> deptDtoList = deptMapper.getListDept();

        for(int i =0;i<deptDtoList.size();i++){
            System.out.println(deptDtoList.get(i).toString());
        }
    }

    @Test
    void updateDept(){
        DeptDto deptDto = new DeptDto();
        deptDto.setDeptId(6L);
        deptDto.setDeptName("디자인새팀");
        deptDto.setVcTo(0L);
        int result = deptMapper.updateDept(deptDto);
        System.out.println("{}{}{}{RESULT}{}{}{} === "+result);
    }

//    @Test
//    void insertDept(){
//        DeptDto deptDto = new DeptDto();
//        deptDto.setDeptName("미래전략팀");
//        deptDto.setVcTo(0L);
//        deptMapper.insertDept(deptDto);
//    }


}
