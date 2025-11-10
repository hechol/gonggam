package com.web.dto;

import com.web.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long id;

    private String title;

    private String content;

    private Member writer;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
