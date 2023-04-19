package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.RemainVcTo;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcReqDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VcReqService {

    /*휴가 신청 내역*/
    public Map<String, Object> getVcReqList(Criteria cri, Long userId);

    /*휴가 신청 내역 상세 (user id 비교)*/
    public VcReqDto getVcReqCompared(Long reqId, Long currUserId);

    /*휴가 신청 내역 상세*/
    public VcReqDto getVcReq(Long reqId);

    /*휴가 신청*/
    public void createVcReq(VcReqDto dto, MultipartFile[] uploadFiles) throws Exception;

    /*휴가 신청 상태 변경*/
    public boolean updateVcReqStatus(VcReqDto dto);

    /*휴가 결재(승인/반려)*/
    public boolean approvalVcRequestStatus(UserDto userDto, Long vcReqId, String status,String comment);

    /*휴가 결재 내역 조회*/
    Map<String, Object> getApprovalVcRequestList(UserDto userDto, Criteria cri);

    /*팀 휴가 승인된 내역 조회*/
    List<VcReqDto> findMyTeamVacation(UserDto userDto);

    /*휴가 신청 일자별로 잔여 TO 계산*/
    public List<RemainVcTo> calcRemainTOByVcReqs(UserDto userDto) throws Exception;

}
