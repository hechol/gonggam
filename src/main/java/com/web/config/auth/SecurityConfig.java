package com.web.config.auth;

import com.web.config.CustomAuthenticationEntryPoint;
import com.web.config.auth.handler.OAuth2LoginSuccessHandler;
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
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           OAuth2LoginSuccessHandler successHandler,
                                           PersistentTokenBasedRememberMeServices rememberMeServices) throws Exception {
//        http.csrf().disable();
        //.headers().frameOptions().disable()
        //.loginPage("/members/login")

        http.oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService);

        http.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID");

        http.authorizeRequests()
//            .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile", "/members/login").permitAll()
            .requestMatchers("/**").permitAll()
                // "/cartItem/**"
            .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
            .anyRequest().authenticated();

        http.exceptionHandling().
                authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        http.rememberMe(remember -> remember
                .rememberMeServices(rememberMeServices)
                .tokenValiditySeconds(60 * 60 * 24 * 14) // 14일
        );

    /*            http.sessionManagement()
                        .invalidSessionUrl("/members/login");*/
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeServices(CustomUserDetailsService userDetailsService) {
        PersistentTokenBasedRememberMeServices services =
                new PersistentTokenBasedRememberMeServices("remember-me-key", userDetailsService, new InMemoryTokenRepositoryImpl());
        services.setAlwaysRemember(true); // 체크박스 없이 발급
        return services;
    }
}
