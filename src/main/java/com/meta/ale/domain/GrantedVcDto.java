package com.meta.ale.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantedVcDto {
    private Long vcId;
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date grantedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expiredDate;
    private Long vcDays;
    private Long remainDays;
    private VcTypeDto vcTypeDto;
    private EmpDto empDto;
}
