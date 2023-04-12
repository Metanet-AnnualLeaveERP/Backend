package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.VcReqDto;

import java.util.Map;

public interface VcReqService {

    /*휴가 신청 내역*/
    public Map<String, Object> getVcReqList(Criteria cri, Long userId);

    /*휴가 신청 내역 상세 (user id 비교)*/
    public VcReqDto getVcReqCompared(Long reqId, Long currUserId);

    /*휴가 신청 내역 상세*/
    public VcReqDto getVcReq(Long reqId);

    /*휴가 신청*/
    public void createVcReq(VcReqDto dto);

    /*휴가 신청 상태 변경*/
    public boolean updateVcReqStatus(VcReqDto dto);

    /*휴가 결재(승인/반려)*/

    /*휴가 결재 내역 조회*/
}
