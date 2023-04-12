package com.meta.ale.mapper;

import com.meta.ale.domain.VcReqDto;
import org.apache.ibatis.annotations.Mapper;

/* Vacation Request */
@Mapper
public interface VcReqMapper {

    /*휴가 신청 내역 조회*/

    /*휴가 신청 내역 상세 조회*/

    /*휴가 신청*/
    public void insertVcReq(VcReqDto dto);

    /*휴가 결재(승인/반려)*/

    /*휴가 결재 내역 조회*/
}
