package com.meta.ale.api;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UsePlanDto;
import com.meta.ale.domain.UserDto;
import com.meta.ale.service.UsePlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/useplan")
public class UsePlanRestController {

    private final UsePlanService usePlanService;

    @GetMapping
    public Map<String, Object> getUsePlanList(@RequestParam(required = false, defaultValue = "all") String keyword,
                                              @AuthenticationPrincipal UserDto userDto,
                                              Criteria criteria) throws Exception {
        criteria.setKeyword(keyword);
        return usePlanService.getUsePlanList(criteria, userDto);
    }

    @GetMapping("/{plan_id}")
    public UsePlanDto getUsePlan(@PathVariable("plan_id") Long planId) throws Exception {
        return usePlanService.getUsePlanByPlanId(planId);
    }

    @PutMapping
    public ResponseEntity<?> modifyUsePlan(@RequestBody UsePlanDto usePlanDto) throws Exception {
        if (usePlanService.modifyUsePlan(usePlanDto)) {
            return ResponseEntity.ok().body("사용 계획이 수정되었습니다.");
        }
        return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    }

    @PostMapping
    public ResponseEntity<?> addUsePlan(@RequestBody UsePlanDto usePlanDto) throws Exception {
        if (usePlanService.addUsePlan(usePlanDto)) {
            return ResponseEntity.ok().body("사용 계획이 추가되었습니다.");
        }
        return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    }

}
