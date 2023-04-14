package com.meta.ale.mapper;

import com.meta.ale.domain.CancelDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CancelMapper {
    /*휴가 취소 내역 조회*/
    public List<CancelDto> getCancelList(HashMap<String, Object> hashMap);

    /*휴가 취소 내역 상세 조회*/
    public CancelDto getCancel(Long cancelId);

    /*휴가 취소*/
    public void insertCancel(CancelDto dto);

    /*휴가 취소 결재(승인/반려)*/

    /*휴가취소 승인 / 휴가취소 반려*/

    /*휴가 취소 상태 변경*/
    public int updateCancelStatus(CancelDto dto);

    /*휴가 신청 개수 (페이징 처리용)*/
    public Long getCancelCount(Long userId);
}
