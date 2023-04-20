package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.EmpDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmpServiceTests {

    @Autowired
    private EmpService empService;

    @Test
    void getEmpInfoTest() throws Exception {
        Long empId = 1L;
        empService.getEmpInfo(empId);
    }

    @Test
    void getEmpList() throws Exception {
        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);
        criteria.setKeyword("전체,이름,전체");

        empService.getEmpList(criteria);
    }

    @Test
    void managerInfoTest() throws Exception {
        Long mgrId = 1L;
        empService.getEmpByMgrId(mgrId);
    }

    @Test
    void myInfoTest() throws Exception {
        Long userId = 17L;
        empService.findEmpByUserId(userId);
    }









//    @Test
//    public void empOverTwoYrLeaveDate(){
//        try {
//            //크론스케줄러 사원 삭제 테스트 코드
////            empService.deleteEmpOverTwoYrLeaveDate();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }



}
