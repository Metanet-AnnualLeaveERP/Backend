//package com.meta.ale.api;
//
//import com.meta.ale.domain.Criteria;
//import com.meta.ale.domain.UserDto;
//import com.meta.ale.domain.YearRemainAlDto;
//import com.meta.ale.service.YearRemainAlService;
//import io.swagger.annotations.Api;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/year-remain")
//@Api(tags = "연차별 ",description = "")
//public class YearRemainAlRestController {
//
//    private final YearRemainAlService yearRemainAlService;
//
//    // 연차별 남은 휴가 리스트
//    @GetMapping
//    public Map<String, Object> getYearRemainAlList(@RequestParam(required = false, defaultValue = "all") String keyword,
//                                                   @AuthenticationPrincipal UserDto userDto,
//                                                   Criteria criteria) throws Exception {
//        criteria.setKeyword(keyword);
//        return yearRemainAlService.getYearRemainAlList(criteria, userDto);
//    }
//
//    // 지난 연차 사용내역 상세 조회
//    @GetMapping("/{al_id}")
//    public YearRemainAlDto getTearRemainInfo(@PathVariable("al_id") Long alId) throws Exception {
//        return yearRemainAlService.getYearRemainAlInfo(alId);
//    }
//}
