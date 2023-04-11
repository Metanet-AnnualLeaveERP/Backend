package com.meta.ale.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearRemainAlDto {
    private Long alId;
    private String year;
    private Long remainAl;
    private EmpDto empDto;
}
