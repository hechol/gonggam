package com.web.config.auth.dto;

import com.web.constant.Role;
import com.web.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String resourceServerName;
    private String oauthId;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String resourceServerName, String nameAttributeKey, String oauthId, String name, String email, String picture) {
        this.resourceServerName = resourceServerName;
        this.oauthId = oauthId;
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver(registrationId, "id", attributes);
        }

        return ofGoogle(registrationId, userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .resourceServerName(registrationId)
                .oauthId((String) attributes.get(userNameAttributeName))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .resourceServerName(registrationId)
                .oauthId((String) response.get(userNameAttributeName))
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /*public Member toEntity() {
       *//* return Member.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();*//*

        Member member = new Member();
        member.setOauthId((String) attributes.get("sub"));
        member.setName(name);
        member.setEmail(email);
        member.setRole(Role.GUEST);
        return member;
    }*/

    public Member toEntity() {
        return new Member(resourceServerName, oauthId, name, email, Role.USER);
    }
}
