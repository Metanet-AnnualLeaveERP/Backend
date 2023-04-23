package com.meta.ale.api;

import com.meta.ale.domain.AnpDocDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;
import com.meta.ale.service.AnpDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = {"연차 사용 요청서"},description = "연차사용 요청서 api")
public class AnpDocRestController {


    private final AnpDocService anpDocService;

    // 연차사용요청서 추가 //
    @ApiOperation(value = "연차사용요청서 추가 api")
    @PostMapping("/annual-promote")
    public void insertAnpDoc(@RequestBody AnpDocDto anpDocDto) {
        try {
            anpDocService.insertAnpDocManually(anpDocDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 연차사용요청서 전체조회 //
    @GetMapping("/annual-promote")
    @ApiOperation(value = "연차사용요청서 전체 조회 api")
    public Map<String, Object> getListAnpDoc(@AuthenticationPrincipal UserDto userDto, Criteria cri) {
        return anpDocService.getListAnpDoc(userDto,cri);
    }

    // 사용요청서 삭제 //
    @DeleteMapping("/annual-promote/{docId}")
    @ApiOperation(value = "연차사용요청서 삭제 api ")
    public ResponseEntity<String> deleteAnpDoc(@PathVariable("docId") Long docId) {
        boolean result = anpDocService.deleteAnpDoc(docId);
        if (result) {
            return new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 상세조회 //
    @GetMapping("/annual-promote/{docId}")
    @ApiOperation(value = "연차사용요청서 상세 조회 api")
    public AnpDocDto getAnpDoc(@PathVariable("docId") Long docId) {
        return anpDocService.getAnpDoc(docId);
    }
}
