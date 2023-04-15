package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.PagenationDTO;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.YearRemainAlDto;
import com.meta.ale.mapper.YearRemainAlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class YearRemainAlServiceImpl implements YearRemainAlService{

    private final YearRemainAlMapper yearRemainAlMapper;

    // 연도별 남은 휴가 리스트
    @Override
    public Map<String, Object> getYearRemainAlList(Criteria criteria, UserDto userDto) throws Exception {

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("pageNum", criteria.getPageNum());
        paramMap.put("amount", criteria.getAmount());
        paramMap.put("keyWord", criteria.getKeyword());
        paramMap.put("userDto", userDto);

        Map<String, Object> res = new HashMap<>();
        res.put("paging", new PagenationDTO(criteria, getYearRemainAlCnt(userDto)));
        res.put("yearRemainALList", yearRemainAlMapper.selectYearRemainAlList(paramMap));

        return res;
    }

    @Override
    public YearRemainAlDto getYearRemainAlInfo(long alId) throws Exception {
        return yearRemainAlMapper.selectYearRemainAlByAlId(alId);
    }

    // 페이징용
    private Integer getYearRemainAlCnt(UserDto userDto) throws Exception {
        return yearRemainAlMapper.selectYearRemainAlCnt(userDto);
    }
}
