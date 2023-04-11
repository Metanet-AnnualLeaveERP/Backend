package com.meta.ale.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptDto {
    private Long deptId;
    private String deptName;
    private Long vcTo;
}
