package com.meta.ale.service;

import com.meta.ale.domain.AnpDocDto;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.GrantedVcDto;
import com.meta.ale.mapper.AnpDocMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnpDocServiceImpl implements AnpDocService {

    private final AnpDocMapper anpDocMapper;
    private final GrantedVacationService gvService;

    @Override
    @Transactional
    public void insertAnpDocScheduler() throws Exception {
        List<GrantedVcDto> grantedVcDtoList = gvService.findPromoteAnnualLeave();

        if (grantedVcDtoList.size() != 0) {
            AnpDocDto anpDocDto = new AnpDocDto();
            Date occurDate = new Date();

            for (GrantedVcDto grantedVcDto : grantedVcDtoList) {
                int totalAnv = grantedVcDto.getVcDays().intValue();
                int remainAnv = grantedVcDto.getRemainDays().intValue();
                int usedAnv = totalAnv - remainAnv;
                EmpDto empDto = grantedVcDto.getEmpDto();
                Date annualOccurDate = grantedVcDto.getGrantedDate();

                anpDocDto.setAll(empDto, totalAnv, occurDate, remainAnv, usedAnv, annualOccurDate);

                anpDocMapper.insertAnpDocMapper(anpDocDto);
            }

        } else {
            System.out.println("연차촉진 날릴 사람 없음.");
        }
    }
}
