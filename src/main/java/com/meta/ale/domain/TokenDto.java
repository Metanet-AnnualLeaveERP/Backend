package com.meta.ale.domain;

import lombok.Builder;

@Builder
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
