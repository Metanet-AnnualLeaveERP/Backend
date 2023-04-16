package com.meta.ale.api;

import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.service.VcTypeService;
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
public class VcTypeRestController {


    private final VcTypeService vcTypeService;

    /* 휴가 유형 추가 */
    @PostMapping("/vacations/type")
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
    public ResponseEntity<List<VcTypeDto>> getListVcType(){
        List<VcTypeDto> vcTypeList = vcTypeService.getListVcType();
        return new ResponseEntity<>(vcTypeList, HttpStatus.OK);
    }


    /* 휴가 유형 수정 */
    @PutMapping("/vacations/type/{typeId}")
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
    public ResponseEntity<String> deleteVcType(@PathVariable("typeId") Long typeId){
        boolean result = vcTypeService.deleteVcType(typeId);
        if(result){
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } else { return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
    }
}
