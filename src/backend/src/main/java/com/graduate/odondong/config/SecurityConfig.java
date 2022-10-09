package com.graduate.odondong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{



        http
                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안함
//                .and()
                .authorizeRequests()
                .antMatchers("/api/bathroom/add", "/api/getBathroomInfo", "/api/bathroom/list", "/api/**").permitAll()
                // swagger
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/health").permitAll()
                .anyRequest().authenticated().and()
                .formLogin()
//                .and() // form 태그 만들어서 로그인을 안함
//                .httpBasic()// 기본 방식 안쓰고 Bearer(jwt) 방법 사용할 것 -> 현재는 기본 방식 사용
                ;

        return http.build();
    }
}
