package com.joney.shop.Common;

import com.joney.shop.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(myUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);

        return authenticationManagerBuilder.build();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //개발을 위해 csrf 일단 끄기
        //내 사이트의 중요한 form에 랜덤 문자를 전송함, 로그인이 필요한 폼이나 .. 등등
        //jwt 입장권을 hearders에 넣어서 보내면 csrf 문제 해결하기 때문에
        // csrf가 필요 없을수도, 하지만 session방식은 필요함


        http.csrf((csrf) -> csrf.disable());

        //로그인 시, 세션 데이터 생성하지 말아주세요.
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.addFilterBefore(new JwtFilter(), ExceptionTranslationFilter.class);

        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll()
        );

        //폼전송말고 Ajax + 수동 로그인을 할 거라서 주석처리 해줌.
//        http.formLogin((formLogin)
//                -> formLogin.loginPage("/login")
//                .defaultSuccessUrl("/")
//
//                //실패시 이동할 페이지, 기본적으로 login/error? 여기로 이동
//                .failureUrl("/fail")
//        );

        http.logout(logout -> logout.logoutUrl("/logout"));

        return http.build();

    }
}