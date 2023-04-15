package com.meta.ale.mapper;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcReqDto;
import com.meta.ale.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class VcReqMapperTest {

    @Autowired
    private VcReqMapper vcReqMapper;

    @Autowired
    private UserService userService;
    @Test
    void insertVcReq() {
        for (int i = 1; i <= 3; i++) {
            VcReqDto dto = new VcReqDto();

            // 시작일
            Date startDate = new Date();
            startDate.setDate(startDate.getDate() + i);
            dto.setStartDate(startDate);

            // 종료일
            Date endDate = new Date();
            endDate.setDate((dto.getStartDate().getDate()) + i);
            dto.setEndDate(endDate);

//            dto.setVcType("연차");
            dto.setReqDays(2L);
            dto.setComments(null);
            dto.setStatus("자동승인");
            dto.setAprvDate(null);
            dto.setFilePath(null);

            // emp
            EmpDto emp = new EmpDto();
            emp.setEmpId(1L);
            dto.setEmpDto(emp);

            vcReqMapper.insertVcReq(dto);
        }
    }

    @Test
    void getVcReqList() {
        // Mapper에 들어갈 파라미터 map으로 변환
        HashMap<String, Object> map = new HashMap<String, Object>();
        Criteria cri = new Criteria();

        map.put("pageNum", cri.getPageNum());
        map.put("amount", cri.getAmount());
        map.put("userId", 2L);

        List<VcReqDto> list = vcReqMapper.getVcReqList(map);
        list.forEach(item -> System.out.println("휴가요청) " + item));
    }

    @Test
    void getVcReq() {
        VcReqDto dto = vcReqMapper.getVcReq(1L);
        System.out.println(dto.toString());
    }

    @Test
    void updateVcReqStatus() {
        VcReqDto dto = vcReqMapper.getVcReq(1L);
        dto.setStatus("취소");
        System.out.println(vcReqMapper.updateVcReqStatus(dto));
        System.out.println(dto.toString());
    }

    @Test
    void getVcReqCount() {
        System.out.println(vcReqMapper.getVcReqCount(1L));
    }

    @Test
    void getVcReqByDeptTest() throws Exception {
        Optional<UserDto> user = userService.getByEmpNum("emp_3");
        List<VcReqDto> data =vcReqMapper.getVcReqByDept(user.get());
        for(VcReqDto v: data){
            System.out.println(v);
        }
    }


}