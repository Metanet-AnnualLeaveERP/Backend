package com.meta.ale.service;

import com.meta.ale.domain.VcReqDto;
import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.domain.VcTypeTotalDto;

public interface VcTypeTotalService {

    boolean updateVcTotalCount(VcTypeTotalDto vcTypeTotalDto);

    VcTypeTotalDto getVcTotalByTypeAndEmpId(VcReqDto vcReqDto);

    boolean updateVcTypeTotalByTotalId(VcTypeTotalDto vcTotal);
}
