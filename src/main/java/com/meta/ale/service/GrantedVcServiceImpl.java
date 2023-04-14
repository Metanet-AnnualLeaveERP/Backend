package com.meta.ale.service;

import com.meta.ale.domain.*;
import com.meta.ale.mapper.GrantedVcMapper;
import com.meta.ale.mapper.VcTypeTotalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class GrantedVcServiceImpl implements GrantedVcService{

    @Autowired
    GrantedVcMapper mapper;

    @Autowired
    VcTypeTotalMapper totalMapper;

    @Override
    public Map<String, Object> getListGrantedVc(Criteria criteria) {
        HashMap<String, Object> dto = new HashMap<>();
        dto.put("pageNum", criteria.getPageNum());
        dto.put("amount", criteria.getAmount());

        Map<String, Object> map = new HashMap<>();
        map.put("paging", new PagenationDTO(criteria, getGrantedVcCount()));
        map.put("grantedVcs", mapper.getListGrantedVc(dto));
        return map;
    }

    @Override
    public GrantedVcDto getGrantedVc(Long vcId){
        return mapper.getGrantedVc(vcId);
    }

    @Override
    @Transactional
    public boolean deleteGrantedVc(Long vcId) {
        GrantedVcDto gvDto = mapper.getGrantedVc(vcId);
        Long remainDays = gvDto.getRemainDays();
        EmpDto empDto = gvDto.getEmpDto();
        VcTypeDto typeDto = gvDto.getVcTypeDto();

        int result = mapper.deleteGrantedVc(vcId);
        if(result != 0 ){
            VcTypeTotalDto totalDto = new VcTypeTotalDto();
            totalDto.setCnt(remainDays);
            totalDto.setVcTypeDto(typeDto);
            totalDto.setEmpDto(empDto);
            totalMapper.minusVcTypeTotal(totalDto);
            return true;
        } else{ return false; }

    }

    @Override
    @Transactional
    public boolean insertGrantedVc(GrantedVcDto grantedVc){
        try{
            mapper.insertGrantedVc(grantedVc);

            Long typeId = grantedVc.getVcTypeDto().getTypeId();
            Long count = grantedVc.getVcDays();
            Long empId = grantedVc.getEmpDto().getEmpId();

            VcTypeTotalDto vcTypeTotal = new VcTypeTotalDto();
            vcTypeTotal.setCnt(count);

            VcTypeDto vcTypeDto = new VcTypeDto();
            vcTypeDto.setTypeId(typeId);
            vcTypeTotal.setVcTypeDto(vcTypeDto);

            EmpDto empDto = new EmpDto();
            empDto.setEmpId(empId);
            vcTypeTotal.setEmpDto(empDto);

            totalMapper.plusVcTypeTotal(vcTypeTotal);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // 이렇게 한땀한땀 dto통 만들어서 하는게 맞나?


    // 전체 부여휴가 row count
    private int getGrantedVcCount(){
        return mapper.getGrantedVcCount().intValue();
    }


}
