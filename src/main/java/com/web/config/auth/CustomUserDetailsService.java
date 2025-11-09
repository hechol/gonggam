package com.web.config.auth;

import com.web.constant.Role;
import com.web.entity.Member;
import com.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository userRepository;;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*String[] userInfos = username.split(":", 2);
        Member user = userRepository.findByResourceServerNameAndOauthId(userInfos[0], userInfos[1]);*/

        Member user = userRepository.findByMemberId(username);

        // OAuth2 사용자도 임의 비밀번호 없이 DB 기반 Persistent Remember-Me 가능
        return User.builder()
                .username(username)
                .password("")
                .roles(user.getRole().toString())
                .build();
    }
}
