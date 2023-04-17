package com.meta.ale.api;

import com.meta.ale.domain.UserDto;
import com.meta.ale.service.VcTypeTotalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VcTypeTotalRestController {

    private final VcTypeTotalService totalService;

    @GetMapping("/vacations/remain-info")
    public ResponseEntity findVcTypeTotalByEmpId(@AuthenticationPrincipal UserDto userDto){
        // reward(보상/포상 등) , annual(연차)
        return ResponseEntity.ok(totalService.findAllMyVacation(userDto));
    }
}
