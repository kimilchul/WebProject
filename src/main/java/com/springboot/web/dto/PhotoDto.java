package com.springboot.web.dto;

import com.springboot.domain.photo.Photo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhotoDto {
    private String origFileName;
    private String filePath;
    private Long fileSize;

    @Builder
    public PhotoDto(String origFileName, String filePath, Long fileSize){
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public Photo toEntity(){
        return Photo.builder()
                .origFileName(origFileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .build();
    }
}