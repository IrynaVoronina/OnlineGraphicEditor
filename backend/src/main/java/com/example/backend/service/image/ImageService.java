package com.example.backend.service.image;

import com.example.backend.model.entities.image.Image;
import com.example.backend.validation.exeption.UnsupportedFileFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    Image getImageById(Integer id);

    Image createImage(Image image) throws IOException;

    Image uploadImage(MultipartFile file) throws IOException, UnsupportedFileFormatException;

    Image applyVisualEffect(Image image, String effect) throws IOException;

    void deleteImage(Image image);

}
