package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcReqDto;

import java.util.Map;

public interface VcReqService {

    /*휴가 신청 내역 조회*/
    public Map<String, Object> getVcReqList(Criteria cri, Long userId);

    /*휴가 신청 내역 상세 조회*/
    public VcReqDto getVcReq(Long reqId, Long currUserId);

    /*휴가 신청*/
    public void createVcReq(VcReqDto dto);

    /*휴가 신청 상태 변경*/
    public boolean updateVcReqStatus(VcReqDto dto);

    /*휴가 결재(승인/반려)*/
    public void approvalVcRequestStatus(String role, Long vcReqId, String status);

    /*휴가 결재 내역 조회*/
    Map<String, Object> approvalVcRequestList(UserDto userDto, Criteria cri);

}
