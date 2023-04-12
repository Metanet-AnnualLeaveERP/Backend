package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.PagenationDTO;
import com.meta.ale.domain.VcReqDto;
import com.meta.ale.mapper.VcReqMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class VcReqServiceImpl implements VcReqService {

    VcReqMapper vcReqMapper;

    /*휴가 신청 내역 조회*/
    @Override
    public Map<String, Object> getVcReqList(Criteria cri, Long empId) {
        // Mapper에 들어갈 파라미터 map으로 변환
        HashMap<String, Object> vo = new HashMap<String, Object>();
        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("empId", empId);

        // 페이징 처리를 위해 map으로 데이터 리턴
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("paging", new PagenationDTO(cri, getVcReqCount(empId)));
        map.put("vcReqs", vcReqMapper.getVcReqList(vo));
        return map;
    }

    /*휴가 신청 내역 상세 조회*/
    @Override
    public VcReqDto getVcReq(Long reqId) {
        return vcReqMapper.getVcReq(reqId);
    }

    /*휴가 신청*/
    @Override
    public void createVcReq(VcReqDto dto) {
        vcReqMapper.insertVcReq(dto);
    }

    /*휴가 결재(승인/반려)*/

    /*휴가 결재 내역 조회*/

    /* ------------서비스 내부에서 쓸 메소드 -------------- */

    /*휴가 신청 개수 (페이징 처리용)*/
    private int getVcReqCount(Long empId) {
        return vcReqMapper.getVcReqCount(empId).intValue();
    }
}
