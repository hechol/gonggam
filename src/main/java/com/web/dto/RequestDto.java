package com.web.dto;

import com.web.constant.RequestCategory;
import com.web.entity.Request;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class RequestDto {

    private Long id;

    private RequestCategory requestCategory;

    private String title;

    private String content;

    private static ModelMapper modelMapper = new ModelMapper();

    public Request createRequest(){
        return modelMapper.map(this, Request.class);
    }

    public static RequestDto of(Request request){
        return modelMapper.map(request, RequestDto.class);
    }
}