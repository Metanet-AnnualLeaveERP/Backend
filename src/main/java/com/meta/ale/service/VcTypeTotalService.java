package com.meta.ale.service;

import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.VcReqDto;
import com.meta.ale.domain.VcTypeTotalDto;

import java.util.Map;

public interface VcTypeTotalService {

    VcTypeTotalDto getVcTotalByTypeAndEmpId(VcReqDto vcReqDto);

    boolean updateVcTypeTotalByTotalId(VcTypeTotalDto vcTotal);

    Map<String, Object> findAllMyVacation(UserDto userDto);
}
