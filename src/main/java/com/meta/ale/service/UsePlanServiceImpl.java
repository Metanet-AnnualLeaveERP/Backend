package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.PagenationDTO;
import com.meta.ale.domain.UsePlanDto;
import com.meta.ale.domain.UserDto;
import com.meta.ale.mapper.UsePlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UsePlanServiceImpl implements UsePlanService{
    private final UsePlanMapper usePlanMapper;

    @Override
    public Map<String, Object> getUsePlanList(Criteria criteria, UserDto userDto) throws Exception {

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("pageNum", criteria.getPageNum());
        paramMap.put("amount", criteria.getAmount());
        paramMap.put("keyWord", criteria.getKeyword());
        paramMap.put("userDto", userDto);

        Map<String, Object> res = new HashMap<>();
        res.put("paging", new PagenationDTO(criteria, getUsePlanCnt(userDto)));
        res.put("usePlanList", usePlanMapper.selectUsePlanList(paramMap));

        return res;
    }

    @Override
    public UsePlanDto getUsePlanByPlanId(Long planId) throws Exception {
        return usePlanMapper.selectUsePlanByPlanId(planId);
    }

    @Override
    public boolean modifyUsePlan(UsePlanDto usePlanDto) throws Exception {
        return usePlanMapper.updateUsePlan(usePlanDto) > 0;
    }

    @Override
    public boolean addUsePlan(UsePlanDto usePlanDto) throws Exception {
        LocalDate startDate = usePlanDto.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = usePlanDto.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Long useDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        usePlanDto.setUseDays(useDays.intValue());

        return usePlanMapper.insertUsePlan(usePlanDto) > 0;
    }

    @Override
    @Transactional
    public boolean addUsePlanList(List<UsePlanDto> usePlanDtoList) throws Exception {
        UsePlanDto usePlanDto;
        int result = 0;
        for (UsePlanDto planDto : usePlanDtoList) {
            usePlanDto = planDto;
            LocalDate startDate = usePlanDto.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = usePlanDto.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long useDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
            usePlanDto.setUseDays((int) useDays);
            int cnt = usePlanMapper.insertUsePlan(usePlanDto);
            result += cnt;
        }
        return result > 0;
    }

    @Override
    public List<UsePlanDto> selectUserPlanListByDocId(Long docId) {
        return usePlanMapper.selectUserPlanListByDocId(docId);
    }

    // 페이징 용
    private Integer getUsePlanCnt(UserDto userDto) throws Exception {
        return usePlanMapper.selectUsePlanCnt(userDto);
    }
}
