package com.meta.ale.mapper;

import com.meta.ale.domain.GrantedVcDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GrantedVcMapper {

    void insertGrantedVc(GrantedVcDto grantedVc);

    void getListGrantedVc();


}
