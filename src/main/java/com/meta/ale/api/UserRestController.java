package com.meta.ale.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.RefreshTokenDto;
import com.meta.ale.domain.UserDto;
import com.meta.ale.domain.UserInfoResponse;
import com.meta.ale.jwt.JwtUtils;
import com.meta.ale.jwt.TokenRefreshException;
import com.meta.ale.service.EmpService;
import com.meta.ale.service.RefreshTokenService;
import com.meta.ale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
// CORS(Cross-Origin Resource Sharing) 정책을 지원하기 위한 것
// 기본적으로 브라우저는 보안 상의 이유로 다른 도메인에 대한 HTTP 요청을 제한
// 이러한 제한을 우회하려면 서버에서 CORS를 지원
// origins 속성에는 "*"를 지정하였으므로 모든 도메인에서 요청을 허용
// maxAge 속성에는 3600을 지정하였으므로 프리플라이 요청을 1시간 동안 캐시할 수 있도록 설정한 것
public class UserRestController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private EmpService empService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto LoginUserDto) {
        System.out.println(LoginUserDto.toString());
        // Spring Security의 인증 매니저를 사용하여 로그인 요청을 인증
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(LoginUserDto.getUsername(), LoginUserDto.getPassword()));

        // pring Security의 SecurityContextHolder를 사용하여 인증된 사용자 정보를 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인증된 사용자 정보를 가져옴
        UserDto userDto = (UserDto) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDto);

        RefreshTokenDto refreshTokenDto = refreshTokenService.createRefreshToken(userDto.getUserId());

        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshTokenDto.getToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .header("role", userDto.getRole())
                .header("empNum", userDto.getEmpNum())
                .body(new UserInfoResponse(userDto.getEmpNum(), userDto.getRole()));
    }

    @PostMapping("/register/emp")
    public ResponseEntity<?> register(@RequestBody ObjectNode objectNode) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = objectMapper.treeToValue(objectNode.get("userDto"), UserDto.class);
        EmpDto empDto = objectMapper.treeToValue(objectNode.get("empDto"), EmpDto.class);
        
        empService.register(userDto, empDto);
        return ResponseEntity.ok().body(empDto); // body(employeeDto) 바꿔야햐ㅏㅁ. 테스트중

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principle.toString() != "anonymousUser") {
            Long userId = ((UserDto)principle).getUserId();
            refreshTokenService.deleteByUserId(userId);
        }

        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body("로그아웃 되었습니다.");
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            return refreshTokenService.getByToken(refreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshTokenDto::getUserDto)
                    .map(userDto -> {
                        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDto);

                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .body("Token is refreshed successfully!");
                    })
                    .orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in database!"));
        }
        return ResponseEntity.badRequest().body("Refresh Token is empty!");

    }
}
