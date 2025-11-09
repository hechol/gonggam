package com.web.config.auth;

import com.web.config.auth.dto.OAuthAttributes;
import com.web.config.auth.dto.SessionUser;
import com.web.constant.Role;
import com.web.entity.Member;
import com.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        Member user = saveOrUpdate(attributes);

        if(user == null) {
            return null;
        }

//        httpSession.setAttribute("user", new SessionUser(user));

        Map<String, Object> attributeList = new LinkedHashMap<>();
        attributeList.put("id", user.getId().toString());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributeList,
                "id");
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        /*Member user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());*/

        // error
        Member user = userRepository.findByResourceServerNameAndOauthId(attributes.getResourceServerName(), attributes.getOauthId());
        if(user!=null){
            //user.update(attributes.getName());
            // 로그인 실패 (현재 아이디와 동일한 아이디가 다른 소셜플렛폼의 아이디로 존재하는 상황)
            //            if (!user.getResourceServerName().equals(attributes.getResourceServerName())) {
            //                return null;
            //            }
        }else{
            user = userRepository.save(attributes.toEntity());
        }

        return user;
    }
}
