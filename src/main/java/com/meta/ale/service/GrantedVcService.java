package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.GrantedVcDto;
import com.meta.ale.domain.VcTypeDto;

import java.util.List;
import java.util.Map;

public interface GrantedVcService {
    /* 휴가 부여내역 전체조회 */
    Map<String, Object> getListGrantedVc(Criteria criteria);

    GrantedVcDto getGrantedVc(Long vcId);

    boolean deleteGrantedVc(Long vcId);

    boolean insertGrantedVc(GrantedVcDto grantedVc);

    public boolean insertAnnualByEmpOverOneYr() throws Exception;

    public List<GrantedVcDto> findPromoteAnnualLeave() throws Exception;

}
