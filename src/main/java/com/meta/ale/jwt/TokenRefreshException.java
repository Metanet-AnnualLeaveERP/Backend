package com.meta.ale.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
// HTTP 403 Forbidden 응답 코드를 반환하도록 지정
// 이 응답 코드는 클라이언트가 요청한 리소스에 접근할 권한이 없을 때 사용
public class TokenRefreshException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
