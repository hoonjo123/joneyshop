package com.joney.shop.Common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //개발을 위해 csrf 일단 끄기
        //내 사이트의 중요한 form에 랜덤 문자를 전송함, 로그인이 필요한 폼이나 .. 등등
        //jwt 입장권을 hearders에 넣어서 보내면 csrf 문제 해결하기 때문에
        // csrf가 필요 없을수도, 하지만 session방식은 필요함


        http.csrf((csrf) -> csrf.disable());

        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll()
        );
        http.formLogin((formLogin) -> formLogin.loginPage("/login")
                .defaultSuccessUrl("/")

                //실패시 이동할 페이지, 기본적으로 login/error? 여기로 이동
                .failureUrl("/fail")
        );

        return http.build();

    }
}