package com.meta.ale.api;

import com.meta.ale.domain.UserDto;
import com.meta.ale.service.VcTypeTotalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "휴가 종류별 총 휴가 계산",description = "총 부여받은 휴가 관련 api")
public class VcTypeTotalRestController {

    private final VcTypeTotalService totalService;

    @GetMapping("/vacations/remain-info")
    @ApiOperation("사원 총 휴가 조회 api")
    public ResponseEntity findVcTypeTotalByEmpId(@AuthenticationPrincipal UserDto userDto){
        // reward(보상/포상 등) , annual(연차)
        return ResponseEntity.ok(totalService.findAllMyVacation(userDto));
    }
}
