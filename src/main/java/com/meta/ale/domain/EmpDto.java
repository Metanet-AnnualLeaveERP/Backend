package com.meta.ale.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpDto {
    private Long empId;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date hireDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date leaveDate;
    private String position;
    @JsonProperty("pEmail")
    private String pEmail;
    @JsonProperty("cEmail")
    private String cEmail;
    private String tel;
    private UserDto userDto; // Security 적용 전까지 비활성화
    private Long mgrId;
    private DeptDto deptDto;
}
