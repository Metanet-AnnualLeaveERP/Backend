package com.meta.ale.service;

import com.meta.ale.domain.*;
import com.meta.ale.mapper.VcReqMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VcReqServiceImpl implements VcReqService {

    private final VcReqMapper vcReqMapper;

    private final EmpService empService;

    /*휴가 신청 내역 조회*/
    @Override
    public Map<String, Object> getVcReqList(Criteria cri, Long userId) {
        // Mapper에 들어갈 파라미터 map으로 변환
        HashMap<String, Object> vo = new HashMap<String, Object>();
        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("userId", userId);

        // 페이징 처리를 위해 map으로 데이터 리턴
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("paging", new PagenationDTO(cri, getVcReqCount(userId)));
        map.put("vcReqs", vcReqMapper.getVcReqList(vo));
        return map;
    }

    /*휴가 신청 내역 상세 조회*/
    @Override
    public VcReqDto getVcReq(Long reqId, Long currUserId) {
        // 현재 로그인한 userId와 reqId로 가져온 휴가 신청의 userId가 동일하지 않으면 null 반환
        VcReqDto dto = vcReqMapper.getVcReq(reqId);
        EmpDto dbEmp = dto.getEmpDto();
        Long dbUserId = dbEmp.getUserDto().getUserId();
        return currUserId == dbUserId ? dto : null;
//        return vcReqMapper.getVcReq(reqId);
    }

    /*휴가 신청*/
    @Override
    public void createVcReq(VcReqDto dto) {
        vcReqMapper.insertVcReq(dto);
    }

    /*휴가 신청 상태 변경*/
    @Override
    public boolean updateVcReqStatus(VcReqDto dto) {
        return vcReqMapper.updateVcReqStatus(dto) != 0 ? true : false;
    }

    /*휴가 결재(승인/반려)*/
    @Override
    public void approvalVcRequestStatus(String role, Long vcReqId, String status) {
//        보류
        VcReqDto vcReq = new VcReqDto();
        vcReq.setReqId(vcReqId);
        vcReq.setStatus(status);
        vcReqMapper.updateVcReqStatus(vcReq);
    }

    /*휴가 결재 내역 조회*/
    @Override
    public Map<String, Object> approvalVcRequestList(UserDto userDto, Criteria cri) {
        String role = userDto.getRole();
        String status;

        HashMap<String, Object> vo = new HashMap<String, Object>();
        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());

        if (role.equals("ROLE_ADMIN")) {
            status = "관리자 대기중";

        } else {
            status = "대기중";
        }
        vo.put("status",status);
        vcReqMapper.getVcReqListByMgr(vo);
        return null;
    }


    /* ------------서비스 내부에서 쓸 메소드 -------------- */

    /*휴가 신청 개수 (페이징 처리용)*/
    private int getVcReqCount(Long empId) {
        return vcReqMapper.getVcReqCount(empId).intValue();
    }

    private Map<String, Object> approvalVcRequestListByAdmin(){

        return null;
    };
}
