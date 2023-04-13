package com.meta.ale.service;

import com.meta.ale.domain.RefreshTokenDto;
import com.meta.ale.jwt.TokenRefreshException;
import com.meta.ale.mapper.RefreshTokenMapper;
import com.meta.ale.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

    @Value("${ale.jwt.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenMapper refreshTokenMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Optional<RefreshTokenDto> getByToken(String token) {
        return refreshTokenMapper.selectByToken(token);
    }

    @Override
    public RefreshTokenDto createRefreshToken(Long userId) {
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto();

        refreshTokenDto.setUserDto(userMapper.selectByUserId(userId).get());
        refreshTokenDto.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshTokenDto.setToken(UUID.randomUUID().toString());

        refreshTokenMapper.insertToken(refreshTokenDto);

        return refreshTokenDto;
    }

    @Override
    public RefreshTokenDto verifyExpiration(RefreshTokenDto refreshTokenDto) {
        if (refreshTokenDto.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenMapper.deleteByTokenId(refreshTokenDto.getTokenId());
            throw new TokenRefreshException(refreshTokenDto.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return refreshTokenDto;
    }

    @Override
    public Integer deleteByUserId(Long userId) {
        return refreshTokenMapper.deleteByUser(userMapper.selectByUserId(userId).get());
    }
}
