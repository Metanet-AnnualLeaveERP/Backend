package com.meta.ale.service;

import com.meta.ale.domain.RefreshTokenDto;

import java.util.Optional;

public interface RefreshTokenService {
    public Optional<RefreshTokenDto> getByToken(String token);

    public RefreshTokenDto createRefreshToken(Long userId);

    public RefreshTokenDto verifyExpiration(RefreshTokenDto refreshTokenDto);

    public Integer deleteByUserId(Long userId);
}
