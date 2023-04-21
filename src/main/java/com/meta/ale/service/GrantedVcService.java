package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.GrantedVcDto;
import com.meta.ale.domain.VcReqDto;

import java.util.List;
import java.util.Map;

public interface GrantedVcService {
    /* 휴가 부여내역 전체조회 */
    Map<String, Object> getListGrantedVc(Criteria criteria);

    GrantedVcDto getGrantedVc(Long vcId);

    boolean deleteGrantedVc(Long vcId);

    boolean insertGrantedVc(GrantedVcDto grantedVc);

    boolean insertAnnualByEmpOverOneYr() throws Exception;

    List<GrantedVcDto> findPromoteAnnualLeave() throws Exception;

    boolean updateAnnualCnt(GrantedVcDto grantedVcDto);

    // 올해 부여된 연차 부여를 찾기 위한 서비스
    GrantedVcDto findByExpiredDateAndEmpIdAndTypeId(VcReqDto vcReqDto);
    
    //연차촉진문서함에서 empId 로 연차정보 찾아서 추가할 때 사용
    GrantedVcDto getAnnualLeaveByEmpId(Long empId);

}
