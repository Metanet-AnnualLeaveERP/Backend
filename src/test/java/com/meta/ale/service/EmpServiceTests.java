package com.meta.ale.service;

import com.meta.ale.domain.EmpDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmpServiceTests {

    @Autowired
    private EmpService empService;
    @Test
    public void empOverTwoYrLeaveDate(){
        try {
            //크론스케줄러 사원 삭제 테스트 코드
//            empService.deleteEmpOverTwoYrLeaveDate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getEmpByMgrId() {
        EmpDto dto = empService.getEmpByMgrId(11L);

        System.out.println(dto);

    }

    @Test
    void calculateRemainTO() throws Exception {
        empService.calculateVcToByDept(2L);
    }
}
