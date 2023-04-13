package com.meta.ale.mapper;

import com.meta.ale.domain.RefreshTokenDto;
import com.meta.ale.domain.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {
    public Optional<RefreshTokenDto> selectByToken(String token);

    public Integer insertToken(RefreshTokenDto refreshTokenDto);

    public Integer deleteByTokenId(Long tokenId);

    public Integer deleteByUser(UserDto userDto);
}
