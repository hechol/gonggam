package com.web.dto;

import com.web.constant.RequestCategory;
import com.web.entity.Request;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
public class RequestFormDto {

    private RequestCategory requestCategory;

    @NotEmpty(message = "제목은 필수 입력 값입니다.")
    private String title;
    private static ModelMapper modelMapper = new ModelMapper();

    @NotEmpty(message = "내용은 필수 입력 값입니다.")
    private String content;

    public Request create() {
        return modelMapper.map(this, Request.class);
    }
}
