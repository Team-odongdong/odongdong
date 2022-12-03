package com.graduate.odondong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.graduate.odondong.config.Security.OAuth2AuthenticationSuccessHandler;
import com.graduate.odondong.service.OAuth.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig{

    private final CustomOAuth2UserService customOAuth2UserService;
	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();

        http
            .authorizeRequests()
			.antMatchers("/api/user/**").authenticated()
            .antMatchers("/api/**", "/oauth2/authorization/**").permitAll()
            // swagger
            .antMatchers("/swagger-ui/**", "/swagger-ui.html", "/swagger/**", "/swagger-resources/**", "/v2/api-docs").permitAll()
            .antMatchers("/health").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
            .and()
            .logout()
            .logoutSuccessUrl("/")
            .and()
            .oauth2Login()
            .defaultSuccessUrl("/api/user/test")
			.failureUrl("/api/user/login/failure")
			.successHandler(oAuth2AuthenticationSuccessHandler)
            .userInfoEndpoint()
            .userService(customOAuth2UserService);

        return http.build();
    }

}