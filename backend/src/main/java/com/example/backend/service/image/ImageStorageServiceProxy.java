package com.example.backend.service.image;

import com.example.backend.model.entities.image.Image;
import com.example.backend.utilities.FileSystemOperation;
import com.example.backend.validation.exeption.ExistenceException;
import com.example.backend.validation.exeption.LocationException;
import com.example.backend.validation.exeption.UnsupportedFileFormatException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service("ImageStorageServiceProxy")
public class ImageStorageServiceProxy implements ImageStorageService {

    private final ImageStorageService imageStorageService;

    private final FileSystemOperation fileSystemOperation;


    public ImageStorageServiceProxy(@Qualifier("imageStorageServiceImpl") ImageStorageService imageStorageService,
                                    FileSystemOperation fileSystemOperation) {
        this.imageStorageService = imageStorageService;
        this.fileSystemOperation = fileSystemOperation;
    }


    @Override
    @Transactional
    public Image saveImage(Image image) throws IOException, LocationException, ExistenceException {

        String location = image.getLocation();

        if (location == null) {
            throw new LocationException("Specify the location");
        } else {

            imageStorageService.saveImage(image);
            fileSystemOperation.saveImageToFileSystem(image);

            return image;
        }
    }

    @Override
    @Transactional
    public Image saveImageAs(Image image) throws IOException, ExistenceException{

        Image imageToSave = imageStorageService.saveImageAs(image);
        String filePath = fileSystemOperation.getFilePath(imageToSave);

        if (Files.exists(Path.of(filePath))) {
            throw new ExistenceException("Image with the same name already exists in this directory");
        }

        fileSystemOperation.saveImageToFileSystem(imageToSave);

        return imageToSave;
    }
}

