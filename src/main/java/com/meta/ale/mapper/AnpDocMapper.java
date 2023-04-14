package com.meta.ale.mapper;

import com.meta.ale.domain.AnpDocDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnpDocMapper {

    void insertAnpDocMapper(AnpDocDto anpDocDto);
}
