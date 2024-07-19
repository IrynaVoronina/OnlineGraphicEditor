package com.example.backend.utilities;

import com.example.backend.model.entities.image.Image;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileSystemOperation {
    public String getFilePath(Image image) {
        return image.getLocation() + File.separator + image.getName() + "."
                + String.valueOf(image.getFormat()).toUpperCase();
    }

    public void saveImageToFileSystem(Image image) throws IOException {
        String filePath = getFilePath(image);

        Files.write(Path.of(filePath), image.getLayers().get(0).getByteData());
    }
}
