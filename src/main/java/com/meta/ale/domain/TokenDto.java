package com.meta.ale.domain;

import lombok.Builder;

@Builder
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}

// Security 적용 전까지 비활성화