package com.web.config.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final PersistentTokenBasedRememberMeServices rememberMeServices;

    public OAuth2LoginSuccessHandler(PersistentTokenBasedRememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        // DB 기반 Remember-Me 쿠키 발급
        rememberMeServices.loginSuccess(request, response, authentication);
        try {
            response.sendRedirect("/home"); // 로그인 성공 후 리다이렉트
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
