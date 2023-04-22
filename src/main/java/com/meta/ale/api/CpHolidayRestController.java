package com.meta.ale.api;

import com.meta.ale.domain.CpHolidayDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.service.CpHolidayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = "사내공휴일",description = "사내공휴일 api")
public class CpHolidayRestController {

    private final CpHolidayService cpHolidayService;

    @GetMapping("/cpholiday")
    @ApiOperation("사내 공휴일 조회 api")
    public Map<String, Object> getCpHolidayList(@RequestParam(required = false, defaultValue = "all") String keyword,
                                                Criteria criteria) throws Exception {
        criteria.setKeyword(keyword);
        return cpHolidayService.getCpHolidayList(criteria);
    }

    @GetMapping("/cpholiday/{holidayId}")
    @ApiOperation("사내공휴일 상세 조회(설명과 일정) api ")
    public CpHolidayDto getCpHolidayInfo(@PathVariable Long holidayId) throws Exception {
        return cpHolidayService.getCpHolidayInfo(holidayId);
    }

    @PostMapping("/admin/cpholiday")
    @ApiOperation("사내공휴일 추가(ex: 설립일) api")
    public ResponseEntity<?> addCpHoliday(@RequestBody CpHolidayDto cpHolidayDto) throws Exception {
        if (cpHolidayService.addCpHoliday(cpHolidayDto)) {
            return ResponseEntity.ok().body(cpHolidayDto); // 테스트 중 cpHolidayDto 바꿔야할수더?
        }
        return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    }

    @DeleteMapping("/admin/cpholiday/{holidayId}")
    @ApiOperation("사내 공휴일 삭제 api ")
    public ResponseEntity<?> removeCpHoliday(@PathVariable Long holidayId) throws Exception {
        if (cpHolidayService.removeCpHoliday(holidayId)) {
            return ResponseEntity.ok().body("사내 공휴일 삭제 완료");
        }
        return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    }

    @PutMapping("/admin/cpholiday")
    @ApiOperation("사내공휴일 수정 api")
    public ResponseEntity<?> modifyCpHoliday(@RequestBody CpHolidayDto cpHolidayDto) throws Exception {
        if (cpHolidayService.modifyCpHoliday(cpHolidayDto)) {
            return ResponseEntity.ok().body("사내 공휴일 수정 완료");
        }
        return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    }

}
