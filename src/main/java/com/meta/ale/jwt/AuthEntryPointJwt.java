package com.meta.ale.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    // 이 메서드는 인증되지 않은 사용자가 보호된 HTTP 리소스를 요청거나 인증 예외가 발생할 때마다 트리거
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());    // 인증 예외의 메시지를 로그로 출력

        // 응답으로 전달할 JSON 형식의 메시지를 생성
        // 이 메시지는 응답의 Content-Type을 APPLICATION_JSON으로 설정하고,
        // 상태 코드를 HttpServletResponse.SC_UNAUTHORIZED로 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Map을 사용하여 JSON 객체를 생성
        // 이 객체는 "status", "error", "message", "path"라는 키를 가지며,
        // 각각의 값으로는 상태 코드, 오류 메시지, 예외 메시지, 요청 경로 정보를 가짐.
        final Map<String, Object> body = new HashMap<>();
        body.put("stats", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());
        // HTTP 401 Unauthorized 상태 코드는 클라이언트가 요청한 리소스에 대한 인증이 필요하다는 것을 나타냄
        // 이 상태 코드는 일반적으로 클라이언트가 유효한 자격 증명(예: 사용자 이름 및 비밀번호)을 제공하지 않은 경우에 반환됨

        // ObjectMapper를 사용하여 생성된 JSON 객체를 response.getOutputStream()으로 전달
        // 이를 통해 생성된 JSON 객체가 클라이언트에게 반환됩니다.
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);

        // ObjectMapper는 Jackson 라이브러리에서 제공하는 클래스로,
        // Java 객체를 JSON 문자열로 변환하거나, 반대로 JSON 문자열을 Java 객체로 변환하는 기능을 제공
        // Java 객체를 JSON 문자열로 변환하기 위해서는 ObjectMapper의 writeValueAsString() 메서드를 호출
        // 이 메서드는 Java 객체를 JSON 형식의 문자열로 변환하여 반환
        // 반대로, JSON 문자열을 Java 객체로 변환하기 위해서는 ObjectMapper의 readValue() 메서드를 호출
        // 이 메서드는 JSON 문자열과 변환할 클래스 타입을 매개변수로 받아서, JSON 문자열을 Java 객체로 변환하여 반환

        // writeValue(), writeValueAsString() 차이
        // writeValue() 메서드는 출력 스트림이나 writer에 직접 쓰기 위해 사용
        // writeValueAsString() 메서드는 변환 결과를 문자열로 사용하기 위해 사용

    }
}
