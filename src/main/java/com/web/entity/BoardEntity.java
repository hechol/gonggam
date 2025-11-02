package com.web.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500) //컬럼의 길이와 null허용여부
    private String title;

    @Column(length = 2000)
    private String content;

    @Column(length = 50)
    private String writer;

//    long writer2;

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }


}