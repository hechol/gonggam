package com.web.entity;

import com.web.constant.RequestCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="request")
@Getter
@Setter
@ToString
public class Request {

    @Id
    @Column(name="request_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RequestCategory requestCategory;

    @Lob
    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;
}
