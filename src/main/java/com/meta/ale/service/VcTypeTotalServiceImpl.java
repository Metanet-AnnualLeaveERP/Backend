package com.meta.ale.service;

import com.meta.ale.domain.*;
import com.meta.ale.mapper.VcTypeTotalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VcTypeTotalServiceImpl implements VcTypeTotalService {

    private final VcTypeTotalMapper vcTypeTotalMapper;

    private final EmpService empService;

    private final GrantedVcService gvService;


    @Override
    public VcTypeTotalDto getVcTotalByTypeAndEmpId(VcReqDto vcReqDto) {
        //vcReq를 알고 있기에 그걸 통해 조회해야함.
        return vcTypeTotalMapper.getVcTotalByTypeAndEmpId(vcReqDto);
    }

    @Override
    public boolean updateVcTypeTotalByTotalId(VcTypeTotalDto vcTotal) {
        return vcTypeTotalMapper.updateVcTypeTotalByTotalId(vcTotal) != 1;
    }

    @Override
    public Map<String,Object> findAllMyVacation(UserDto userDto) {
        Map<String,Object> remainVc=  new HashMap<>();
        if (userDto.getRole().equals("ROLE_ADMIN")) {
            return null;
        }
        EmpDto empDto= empService.findEmpByUserId(userDto.getUserId());
        Long empId = empDto.getEmpId();
        VcTypeDto vcTypeDto = new VcTypeDto();
        vcTypeDto.setTypeId(1L);
        VcReqDto vcReqDto = new VcReqDto();
        vcReqDto.setEmpDto(empDto);
        vcReqDto.setVcTypeDto(vcTypeDto);
        List<VcTypeTotalDto> totalDtoList =vcTypeTotalMapper.findAllMyVacation(empId);
        GrantedVcDto gvDto = gvService.findByExpiredDateAndEmpIdAndTypeId(vcReqDto);
        remainVc.put("annual",gvDto);
        remainVc.put("reward",totalDtoList);
        return remainVc;
    }
}
