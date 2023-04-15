package com.meta.ale.service;

import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.domain.VcTypeTotalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VcTypeTotalServiceImpl implements VcTypeTotalService{


    @Override
    public boolean updateVcTotalCount(VcTypeTotalDto vcTypeTotalDto) {

        return false;
    }

    @Override
    public VcTypeDto getVcTotalByTypeAndEmpId() {
        return null;
    }
}
