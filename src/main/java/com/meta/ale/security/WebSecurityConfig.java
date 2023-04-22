package com.meta.ale.security;

import com.meta.ale.jwt.AuthEntryPointJwt;
import com.meta.ale.jwt.AuthTokenFilter;
import com.meta.ale.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// @EnableGlobalMethodSecurity는 Spring Security에서 메소드 기반 보안을 구성할 수 있도록 지원
// securedEnabled는 @Secured를 사용하여 메서드에 대한 보안을 적용하는 데 사용
// @Secured는 특정 권한이 있는 사용자만 해당 메서드를 호출할 수 있도록 제한하는 데 사용
// jsr250Enabled는 @RolesAllowed를 사용하여 메서드에 대한 보안을 적용하는 데 사용
// @RolesAllowed는 특정 role을 가진 사용자만 해당 메서드를 호출할 수 있도록 제한하는 데 사용
// PrePostEnabled는 @PreAuthorize와 @PostAuthorize 어노테이션을 사용하여 메서드에 대한 보안을 적용
// 이것이 가장 일반적으로 사용되는 메서드 보안 방식(EnableGlobalMethodSecurity provides AOP security on methods.)
// @PreAuthorize는 메소드가 실행되기 전에 권한 검사를 수행하며, 메소드의 실행 여부를 결정
// @PostAuthorize는 메소드가 실행된 후에 권한 검사를 수행하며, 메소드의 실행 결과를 확인하고 반환하기 전에 권한 검사를 수행
@EnableGlobalMethodSecurity(
//        securedEnabled = true
//        jsr250Enabled = true
         prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserService userService;

    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    // DaoAuthenticationProvider는 DB에서 사용자 인증 정보를 가져와 인증을 수행
    // 이를 위해 UserDetailsService 인터페이스를 사용하여 사용자의 정보를 로드하고
    // PasswordEncoder를 사용하여 비밀번호를 검증
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    // AuthenticationManager 인터페이스는 스프링 시큐리티의 핵심 인터페이스 중 하나이며, 인증을 처리하는 데 사용
    // AuthenticationManager 인터페이스는 authenticate() 메서드를 정의하고 있으며,
    // 이 메서드는 인증 객체(Authentication)를 인자로 받아서 인증을 수행하고 인증된 객체(Authentication)를 반환
    // AuthenticationConfiguration은 스프링 부트에서 자동 구성되는 인증 설정 정보를 가지고 있음
    // 이 인증 설정 정보를 이용하여 AuthenticationManager 인스턴스를 생성하고 빈으로 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationEntryPoint는 Spring Security에서 인증되지 않은 사용자가 보호된 자원에 접근하려고 할 때 호출되는 인터페이스
    // 이 인터페이스를 사용하여 인증되지 않은 사용자가 보호된 자원에 접근하려고 할 때 어떤 동작을 취해야 하는지를 정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/manager/**").hasAnyRole("MGR", "ADMIN")
                .antMatchers("/emp/**").hasAnyRole("EMP", "MGR", "ADMIN")
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v3/**").permitAll()
                .anyRequest().authenticated();
        // fix H2 database console: Refused to display ' in a frame because it set 'X-Frame-Options' to 'deny'
//        http.headers().frameOptions().sameOrigin();
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
