package com.meta.ale.mapper;

import com.meta.ale.domain.GrantedVcDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface GrantedVcMapper {

    int insertGrantedVc(GrantedVcDto grantedVc);
    List<GrantedVcDto> getListGrantedVc(HashMap<String, Object> hashMap);
    GrantedVcDto getGrantedVc(Long vcId);
    int updateGrantedVc(GrantedVcDto grantedVc);
    int deleteGrantedVc(Long vcId);
    Long getGrantedVcCount();
}
