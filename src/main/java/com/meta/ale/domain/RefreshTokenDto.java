//package com.meta.ale.domain;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.time.Instant;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class RefreshTokenDto {
//    private Long tokenId;
//    private UserDto userDto;
//    private String token;
//    @DateTimeFormat(pattern = "yyyy-MM-dd" )
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    private Instant expiryDate;
//}
