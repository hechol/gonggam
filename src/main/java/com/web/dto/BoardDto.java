package com.web.dto;

import com.web.constant.BoardCategory;
import com.web.entity.BoardEntity;
import com.web.entity.Member;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDto {

    private Long id;

    private String category;

    private String title;

    private String content;

    private Member writer;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    public BoardEntity toEntity(ModelMapper modelMapper) {
        BoardEntity boardEntity = modelMapper.map(this, BoardEntity.class);
//        BoardCategory value = BoardCategory.valueOf("gonggam");
//        BoardCategory value2 = BoardCategory.valueOf("gg");
//        boardEntity.setCategory(value);
        boardEntity.setCategory(BoardCategory.valueOf(getCategory()));
        return boardEntity;
    }
}
