package com.meta.ale.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VcTypeDto {
    private Long typeId;
    private String name;
    private String description;
}
