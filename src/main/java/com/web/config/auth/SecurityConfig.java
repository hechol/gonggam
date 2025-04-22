package com.web.config.auth;

import com.web.config.CustomAuthenticationEntryPoint;
import com.web.constant.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.csrf().disable()
        //.headers().frameOptions().disable()
        //.loginPage("/members/login")

        http.oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService);

        http.logout()
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID");

        http.authorizeRequests()
            .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile", "/members/login").permitAll()
            // "/cartItem/**"
            .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
            .anyRequest().authenticated();

        http.exceptionHandling().
                authenticationEntryPoint(new CustomAuthenticationEntryPoint());

    /*            http.sessionManagement()
                        .invalidSessionUrl("/members/login");*/
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
