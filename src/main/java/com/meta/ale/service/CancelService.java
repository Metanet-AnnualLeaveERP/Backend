package com.meta.ale.service;

import com.meta.ale.domain.CancelDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;

import java.util.Map;

public interface CancelService {

    /*휴가 취소 내역 조회*/
    public Map<String, Object> getCancelList(Criteria cri, Long userId);

    /*휴가 취소 내역 상세 조회*/
    public CancelDto getCancel(Long cancelId, Long currUserId);

    /*휴가 취소*/
    public void createCancel(CancelDto dto, Long reqId);

    /*휴가취소 승인 / 휴가취소 반려 관리자 조회*/
    public Map<String,Object> getApprovalCancelList(UserDto userDto,Criteria cri);

    /*휴가 취소 상태 변경*/
    public boolean updateCancelStatus(CancelDto dto);

    /*휴가 취소 결재*/
    public boolean approvalCancel(Long cancelId, String status);


}
