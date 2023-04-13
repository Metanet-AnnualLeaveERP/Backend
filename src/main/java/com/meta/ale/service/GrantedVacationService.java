package com.meta.ale.service;

import com.meta.ale.domain.GrantedVcDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface GrantedVacationService {

    public boolean insertAnnualByEmpOverOneYr() throws Exception;

    public List<GrantedVcDto> findPromoteAnnualLeave() throws Exception;

}
