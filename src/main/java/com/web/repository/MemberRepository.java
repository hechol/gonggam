package com.web.repository;

import com.web.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //Member findByEmail(String email);
    Member findByResourceServerNameAndOauthId(String resourceServerName, String oauthId);
    Member findByOauthId(String oauthId);
    Member findByMemberId(String memberId);

}