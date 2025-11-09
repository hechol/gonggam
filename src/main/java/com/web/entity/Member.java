package com.web.entity;

import com.web.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="member")
@Getter @Setter
@ToString
public class Member extends BaseEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "oauth_id")
    private String oauthId;

    @Column(name = "resource_server_name")
    private String resourceServerName;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
/*
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }*/

    public Member update(String name) {
        this.name = name;

        return this;
    }

    public Member(){

    }

    public Member(String resourceServerName, String oauthId, String name, String email, Role role) {
        this.memberId = resourceServerName + ":" + oauthId;
        this.resourceServerName = resourceServerName;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
