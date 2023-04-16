package com.meta.ale.mapper;

import com.meta.ale.domain.AnpDocDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AnpDocMapper {

    void insertAnpDocMapper(AnpDocDto anpDocDto);

    List<AnpDocDto> getListAnpDoc(HashMap<String, Object> hashMap);

    Long getAnpDocCount();

    int deleteAnpDoc(Long docId);

    AnpDocDto getAnpDoc(Long docId);
}
