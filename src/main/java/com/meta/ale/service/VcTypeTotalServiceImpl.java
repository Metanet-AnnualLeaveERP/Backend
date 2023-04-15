package com.meta.ale.service;

import com.meta.ale.domain.VcReqDto;
import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.domain.VcTypeTotalDto;
import com.meta.ale.mapper.VcTypeTotalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VcTypeTotalServiceImpl implements VcTypeTotalService{

    private final VcTypeTotalMapper vcTypeTotalMapper;

    @Override
    public boolean updateVcTotalCount(VcTypeTotalDto vcTypeTotalDto) {

        return false;
    }
    @Override
    public VcTypeTotalDto getVcTotalByTypeAndEmpId(VcReqDto vcReqDto) {
        //vcReq를 알고 있기에 그걸 통해 조회해야함.
        return vcTypeTotalMapper.getVcTotalByTypeAndEmpId(vcReqDto);
    }

    @Override
    public boolean updateVcTypeTotalByTotalId(VcTypeTotalDto vcTotal) {
        return vcTypeTotalMapper.updateVcTypeTotalByTotalId(vcTotal) != 1;
    }
}
