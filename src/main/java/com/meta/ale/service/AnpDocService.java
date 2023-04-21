package com.meta.ale.service;

import com.meta.ale.domain.AnpDocDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;

import java.util.Map;

public interface AnpDocService {

    public void insertAnpDocScheduler() throws Exception;

    void insertAnpDocManually(AnpDocDto anpDocDto) throws Exception;

    Map<String, Object> getListAnpDoc(UserDto  userDto, Criteria cri);

    boolean deleteAnpDoc(Long docId);

    AnpDocDto getAnpDoc(Long docId);

}
