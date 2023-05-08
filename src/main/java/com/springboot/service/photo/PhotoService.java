package com.springboot.service.photo;

import com.springboot.domain.photo.Photo;
import com.springboot.domain.photo.PhotoListRepository;
import com.springboot.domain.photo.PhotoRepository;
import com.springboot.web.dto.PhotoDto;
import com.springboot.web.dto.PhotoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepository;

    private final String filePath = "images/";

    @Transactional
    public Long save(PhotoDto photoDto) {
        return photoRepository.save(photoDto.toEntity()).getId();
    }


    @Transactional
    public Photo findById(Long id) {

        Photo photo = photoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

        return photo;
    }

    public boolean downloadPhoto(MultipartFile photoByte) {
        if (!photoByte.isEmpty()) {
            try {
                byte[] imageData = photoByte.getBytes();
                String filePath = "images/" + photoByte.getOriginalFilename();
                FileOutputStream outputStream = new FileOutputStream(filePath);
                outputStream.write(imageData);
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

}