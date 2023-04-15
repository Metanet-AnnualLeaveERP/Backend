package com.meta.ale.mapper;

import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcReqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/* Vacation Request */
@Mapper
public interface VcReqMapper {

    /*휴가 신청 내역 조회*/
    public List<VcReqDto> getVcReqList(HashMap<String, Object> hashMap);

    /*휴가 신청 내역 상세 조회*/
    public VcReqDto getVcReq(Long reqId);

    /*휴가 신청*/
    public void insertVcReq(VcReqDto dto);

    /*휴가 결재(승인/반려)*/

    /*휴가 결재 내역 조회*/
    public List<VcReqDto> getVcReqListByMgr(HashMap<String, Object> hashMap);


    /*휴가 신청 상태 변경*/
    public int updateVcReqStatus(VcReqDto dto);

    /*휴가 신청 개수 (페이징 처리용)*/
    public Long getVcReqCount(Long userId);

    /*휴가 결재 내역 조회 개수(페이징 처리용) */
    public Long getVcReqCountByMgr(HashMap<String, Object> hashMap);

    /* 팀원 자동승인, 승인완료 내역 조회  */
    public List<VcReqDto> getVcReqByDept(UserDto userDto);
}
