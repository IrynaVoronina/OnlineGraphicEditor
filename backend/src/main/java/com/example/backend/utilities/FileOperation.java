package com.example.backend.utilities;

import org.springframework.web.multipart.MultipartFile;

public class FileOperation {

    private final MultipartFile file;

    public FileOperation(MultipartFile file) {
        this.file = file;
    }

    public String getFileFormat(){

        String filename = file.getOriginalFilename();
        String format = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

        return format;
    }

    public String getNameWithoutExtension() {
        String filename = file.getOriginalFilename();
        return filename.substring(0, filename.lastIndexOf("."));
    }
}
