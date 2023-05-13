package com.springboot.web.dto;

import com.springboot.domain.photo.Photo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhotoDto {
    private String originalFileName;
    private String filePath;
    private Long fileSize;

    @Builder
    public PhotoDto(String originalFileName, String filePath, Long fileSize){
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public Photo toEntity(){
        return Photo.builder()
                .originalFileName(originalFileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .build();
    }
}