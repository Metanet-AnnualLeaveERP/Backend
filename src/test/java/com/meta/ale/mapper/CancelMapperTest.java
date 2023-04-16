package com.meta.ale.mapper;

import com.meta.ale.domain.CancelDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.VcReqDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CancelMapperTest {

    @Autowired
    CancelMapper cancelMapper;

    @Autowired
    VcReqMapper vcReqMapper;

    @Autowired
    EmpMapper empMapper;
    @Test
    void getCancelList() {
        // Mapper에 들어갈 파라미터 map으로 변환
        HashMap<String, Object> vo = new HashMap<String, Object>();
        Criteria cri = new Criteria();

        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("userId", 1L);

        List<CancelDto> list = cancelMapper.getCancelList(vo);
        list.forEach(item -> System.out.println("휴가취소) " + item));
    }

    @Test
    void getCancel() {
        CancelDto dto = cancelMapper.getCancel(1L);
        if(dto != null){
        System.out.println(dto.toString());
        }
    }

    @Test
    void insertCancel() {
        VcReqDto req = vcReqMapper.getVcReq(1L);

        CancelDto dto = new CancelDto();
        dto.setCancelStatus("취소요청");
        dto.setReqComm("일정 변경");
        dto.setVcReqDto(req);
    }

    @Test
    void getCancelCntByMgr(){
        Criteria cri = new Criteria();
        EmpDto emp = empMapper.findEmpByUserId(2L);

        Date date = new Date();

        // 팀장
        Long mgrDeptId = emp.getDeptDto().getDeptId();
        // 관리자
//        mgrDeptId = null;

        HashMap<String, Object> vo = new HashMap();
        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("keyword", cri.getKeyword());
        vo.put("deptId", mgrDeptId);

        System.out.println(cancelMapper.getCancelCountByMgr(vo));
    }

    @Test
    void getCancelListByMgr(){
        Criteria cri = new Criteria();
        EmpDto emp = empMapper.findEmpByUserId(2L);

        Date date = new Date();

        // 팀장
        Long mgrDeptId = emp.getDeptDto().getDeptId();
        // 관리자
        mgrDeptId = null;
        HashMap<String, Object> vo = new HashMap();
        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("keyword", cri.getKeyword());
        vo.put("deptId", mgrDeptId);
        cancelMapper.getCancelCountByMgr(vo);
        System.out.println(cancelMapper.getCancelListByMgr(vo));
    }
}