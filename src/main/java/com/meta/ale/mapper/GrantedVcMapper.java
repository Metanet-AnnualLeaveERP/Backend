package com.meta.ale.mapper;

import com.meta.ale.domain.GrantedVcDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import com.meta.ale.domain.VcTypeDto;

import java.util.List;

@Mapper
public interface GrantedVcMapper {

    // 부여받은 휴가의 타입이 연차이면서 empId를 비교해서 가져옴
    public GrantedVcDto findByEmpIdVcType(GrantedVcDto grantedVcDto);
    //1년 초과된 사람들의 연차 추가
    public void insertAnnualGranted(GrantedVcDto grantedVcDto);

    // 1년이 된 사람들의 연차 추가
    public void updateAnnualGranted(GrantedVcDto grantedVcDto);

    public List<GrantedVcDto> findPromoteAnnualLeaveList(VcTypeDto vcTypeDto);
    int insertGrantedVc(GrantedVcDto grantedVc);
    List<GrantedVcDto> getListGrantedVc(HashMap<String, Object> hashMap);
    GrantedVcDto getGrantedVc(Long vcId);
    int updateGrantedVc(GrantedVcDto grantedVc);
    int deleteGrantedVc(Long vcId);
    Long getGrantedVcCount();
}
