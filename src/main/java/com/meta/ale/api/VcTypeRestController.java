package com.meta.ale.api;

import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.service.VcTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* 휴가유형관리 RestController */
@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
@Api(tags = "휴가유형관리", description = "휴가 유형관리 관련 api")
public class VcTypeRestController {


    private final VcTypeService vcTypeService;

    /* 휴가 유형 추가 */
    @PostMapping("/vacations/type")
    @ApiOperation("휴가 유형 추가 api")
    public ResponseEntity<String> insertVcType(@RequestBody VcTypeDto vcType) {
        try {
            vcTypeService.insertVcType(vcType);
            return new ResponseEntity<>("DATA INSERT SUCCESS", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 휴가 유형 조회 */
    @GetMapping("/vacations/type")
    @ApiOperation("휴가 유형 조회 api")
    public ResponseEntity<List<VcTypeDto>> getListVcType(){
        List<VcTypeDto> vcTypeList = vcTypeService.getListVcType();
        return new ResponseEntity<>(vcTypeList, HttpStatus.OK);
    }


    /* 휴가 유형 수정 */
    @PutMapping("/vacations/type/{typeId}")
    @ApiOperation("휴가 유형 수정 api")
    public ResponseEntity<String> updateVcType(
            @PathVariable("typeId") Long typeId, @RequestBody VcTypeDto vcTypeDto){
        vcTypeDto.setTypeId(typeId);
        boolean result = vcTypeService.updateVcType(vcTypeDto);
        if(result) {
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 휴가 유형 삭제 */
    @DeleteMapping("/vacations/type/{typeId}")
    @ApiOperation("휴가 유형 삭제 api")
    public ResponseEntity<String> deleteVcType(@PathVariable("typeId") Long typeId){
        boolean result = vcTypeService.deleteVcType(typeId);
        if(result){
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } else { return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
    }
}
