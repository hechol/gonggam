package com.web.entity;

import com.web.constant.BoardCategory;
import com.web.dto.BoardDto;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Entity
@Getter
public class BoardEntity extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ColumnDefault("gonggam")
    @Setter
    @Enumerated(EnumType.STRING)
    private BoardCategory category;

    @Column(length = 500) //컬럼의 길이와 null허용여부
    private String title;

    @Column(length = 2000)
    private String content;

    @ManyToOne
    private Member writer;

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }

    public BoardDto toDto(ModelMapper modelMapper){
        BoardDto boardDto = modelMapper.map(this, BoardDto.class);
        boardDto.setCategory(getCategory().name());
        return boardDto;
    }

}