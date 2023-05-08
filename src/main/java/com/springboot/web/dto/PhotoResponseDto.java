package com.springboot.web.dto;

import com.springboot.domain.photo.Photo;
import lombok.Getter;

@Getter
public class PhotoResponseDto {
    private Long fileId;

    public PhotoResponseDto(Photo entity){
        this.fileId = entity.getId();
    }
}