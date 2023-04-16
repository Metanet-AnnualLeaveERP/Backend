package com.meta.ale.api;

import com.meta.ale.domain.CancelDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.UserDto;
import com.meta.ale.service.CancelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
//@RequestMapping("/api")
public class CancelRestController {

    private CancelService cancelService;

    /*휴가 취소 내역 조회*/
    @GetMapping("/vacations/cancel")
    public Map<String, Object> cancelList(@RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int pagenum,
                                          @AuthenticationPrincipal UserDto user,
                                          Criteria cri) {

        cri.setPageNum(page);
        cri.setAmount(pagenum);

        /* ADMIN or EMP 판별
            userId == 0 -> admin */
        return cancelService.getCancelList(cri, user.getUserId());
    }

    /*휴가 취소 내역 상세 조회*/
    @GetMapping("/vacations/cancel/{cancel_id}")
    public ResponseEntity<Object> cancelDetail(@PathVariable("cancel_id") Long cancelId,
                                               @AuthenticationPrincipal UserDto user) {
        CancelDto dto = cancelService.getCancel(cancelId, user.getUserId());

        if (dto != null) {
            dto.getVcReqDto().getEmpDto().setUserDto(null);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        // 자기가 작성하지 않은 휴가 신청에 접근하면 403 에러와 null 반환
        // 혹은 커스텀 예외 처리
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다");
// 예외 처리 예시
//        if (user == null) {
//            throw new UserNotFoundException(String.format("ID[%s] not found", id));
//        }
    }

    /*휴가 취소*/
    @PostMapping("/emp/vacations/cancel/{vacation-request_id}")
    public ResponseEntity createCancel(@RequestBody CancelDto dto, @PathVariable("vacation-request_id") Long reqId) {
        cancelService.createCancel(dto, reqId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /*휴가취소 승인 / 휴가취소 반려 관리자 조회*/
    @GetMapping("/manager/vacations/cancel")
    public ResponseEntity cancelListByMgr(@AuthenticationPrincipal UserDto userDto,
                                          Criteria cri) {

        Map<String,Object> result= cancelService.getApprovalCancelList(userDto, cri);

        return ResponseEntity.ok(result);
    }

    /*휴가취소 승인 / 휴가취소 반려*/
    @PutMapping("/manager/vacations/cancel/{cancel_id}")
    public ResponseEntity approvalCancel(@PathVariable("cancel_id") Long cancelId,
                                         @RequestParam("status") String status,
                                         @RequestParam("comment")String comment) {

        if (!cancelService.approvalCancel(cancelId, status,comment)) {
            return ResponseEntity.badRequest().body("비정상적인 처리입니다.");
        } ;
        return ResponseEntity.ok("정상 처리 되었습니다.");
    }


}
