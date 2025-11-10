package com.web.config.auth.dto;

import com.web.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;

    public SessionUser(Member user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
