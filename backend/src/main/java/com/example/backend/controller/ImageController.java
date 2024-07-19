package com.example.backend.controller;

import com.example.backend.dto.image.*;
import com.example.backend.mapper.ImageMapper;


import com.example.backend.model.entities.image.Image;
import com.example.backend.service.image.ImageService;
import com.example.backend.service.image.ImageStorageService;

import com.example.backend.service.image.effect.VisualEffect;
import com.example.backend.validation.annotation.EnumConstraint;
import com.example.backend.validation.exeption.ExistenceException;
import com.example.backend.validation.exeption.LayerDeleteException;
import com.example.backend.validation.exeption.LocationException;
import com.example.backend.validation.exeption.UnsupportedFileFormatException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/images")
public class ImageController {

    private final ImageStorageService imageStorageService;
    private final ImageService imageService;
    private final ImageMapper imageMapper;

    public ImageController(@Qualifier("ImageStorageServiceProxy") ImageStorageService imageStorageService,
                           ImageService imageService,
                           ImageMapper imageMapper) {
        this.imageStorageService = imageStorageService;
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }


    @GetMapping("/{imageId}")
    public ResponseEntity<ImageResponseDto> getImageById(@PathVariable Integer imageId) {
        return ResponseEntity.ok().body(imageMapper.toDto(imageService.getImageById(imageId)));
    }

    @PostMapping("/create")
    public ResponseEntity<ImageResponseDto> createImage(@RequestBody @Valid ImageCreateDto imageCreateDto) {
        try {
            ImageResponseDto createdImage = imageMapper.toDto(imageService.createImage(imageMapper.createDtoToEntity(imageCreateDto)));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdImage);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageResponseDto> uploadImage(@RequestParam("file") MultipartFile file) throws UnsupportedFileFormatException {
        try {
            return ResponseEntity.ok().body(imageMapper.toDto(imageService.uploadImage(file)));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{imageId}/applyVisualEffect")
    public ResponseEntity<ImageResponseDto> applyVisualEffect(@PathVariable Integer imageId,
                                                              @RequestParam("effect") @EnumConstraint(enumClass = VisualEffect.class) String effect) {
        try {
            Image image = imageService.applyVisualEffect(imageService.getImageById(imageId), effect);
            return ResponseEntity.ok().body(imageMapper.toDto(image));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveImage(@RequestParam("imageId") Integer imageId) throws LocationException, ExistenceException {
        try {
            imageStorageService.saveImage(imageService.getImageById(imageId));
            return ResponseEntity.ok().body("Image saved");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Something wrong with bytes");
        }
    }

    @PostMapping("/saveAs")
    public ResponseEntity<String> saveImageAs(@RequestBody @Valid ImageSaveAsDto imageSaveAsDto) throws ExistenceException {
        try {
            imageStorageService.saveImageAs(imageMapper.saveAsDtoToEntity(imageSaveAsDto));
            return ResponseEntity.ok().body("Image saved as");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Something wrong with bytes");
        }
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Integer imageId) {
        Image image = imageService.getImageById(imageId);
        imageService.deleteImage(image);
        return ResponseEntity.ok().build();
    }
}

