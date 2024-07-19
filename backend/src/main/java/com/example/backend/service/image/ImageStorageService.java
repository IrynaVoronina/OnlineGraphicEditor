package com.example.backend.service.image;

import com.example.backend.model.entities.image.Image;
import com.example.backend.validation.exeption.ExistenceException;
import com.example.backend.validation.exeption.LocationException;
import com.example.backend.validation.exeption.UnsupportedFileFormatException;

import java.io.IOException;

public interface ImageStorageService {

    Image saveImage(Image image) throws IOException, LocationException, ExistenceException;

    Image saveImageAs(Image image) throws IOException, ExistenceException;

}
