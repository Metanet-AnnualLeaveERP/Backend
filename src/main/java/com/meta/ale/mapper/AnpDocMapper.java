package com.meta.ale.mapper;

import com.meta.ale.domain.AnpDocDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AnpDocMapper {

    /* 연차촉진문서 생성 */
    void insertAnpDocMapper(AnpDocDto anpDocDto);

    /* 연차촉진문서 내역 불러오기 */
    List<AnpDocDto> getListAnpDoc(HashMap<String, Object> hashMap);

    /* 연차촉진문서 개수 가져오기(Paging) */
    Long getAnpDocCount();

    /* 연차촉진문서 삭제 */
    int deleteAnpDoc(Long docId);

    /* 연차촉진문서 상세 조회 */
    AnpDocDto getAnpDoc(Long docId);
}
