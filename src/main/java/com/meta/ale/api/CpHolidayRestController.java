package com.meta.ale.api;

import com.meta.ale.domain.CpHolidayDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.service.CpHolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CpHolidayRestController {

    private final CpHolidayService cpHolidayService;

    @GetMapping("/cpholiday")
    public Map<String, Object> getCpHolidayList(@RequestParam(required = false, defaultValue = "1") int page,
                                                @RequestParam(required = false, defaultValue = "10") int pagenum,
                                                @RequestParam(required = false, defaultValue = "all") String keyword,
                                                Criteria criteria) throws Exception {
        criteria.setPageNum(page);
        criteria.setAmount(pagenum);
        criteria.setKeyword(keyword);
        return cpHolidayService.getCpHolidayList(criteria);
    }

    @GetMapping("/cpholiday/{holidayId}")
    public CpHolidayDto getCpHolidayInfo(@PathVariable Long holidayId) throws Exception {
        return cpHolidayService.getCpHolidayInfo(holidayId);
    }

    @PostMapping("/admin/cpholiday")
    public ResponseEntity<?> addCpHoliday(@RequestBody CpHolidayDto cpHolidayDto) throws Exception {
        if (cpHolidayService.addCpHoliday(cpHolidayDto)) {
            return ResponseEntity.ok().body(cpHolidayDto); // 테스트 중 cpHolidayDto 바꿔야할수더?
        }
        return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    }

    @DeleteMapping("/admin/cpholiday/{holidayId}")
    public ResponseEntity<?> removeCpHoliday(@PathVariable Long holidayId) throws Exception {
        if (cpHolidayService.removeCpHoliday(holidayId)) {
            return ResponseEntity.ok().body("사내 공휴일 삭제 완료");
        }
        return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    }

    @PutMapping("/admin/cpholiday")
    public ResponseEntity<?> modifyCpHoliday(@RequestBody CpHolidayDto cpHolidayDto) throws Exception{
        if (cpHolidayService.modifyCpHoliday(cpHolidayDto)) {
            return ResponseEntity.ok().body("사내 공휴일 수정 완료");
        }
        return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    }

}
