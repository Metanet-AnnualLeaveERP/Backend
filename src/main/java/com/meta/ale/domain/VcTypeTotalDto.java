package com.meta.ale.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VcTypeTotalDto {
    private Long totalId;
    private Long totalGvCnt;
    private Long cnt;
    private VcTypeDto vcTypeDto;
    private EmpDto empDto;
}
