package com.example.backend.validation.validator;

import com.example.backend.model.enums.Format;
import com.example.backend.validation.exeption.UnsupportedFileFormatException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FormatValidator {

    public static void validate(String format) throws UnsupportedFileFormatException {
        if (format != null) {
            if (!format.equalsIgnoreCase(String.valueOf(Format.png)) &&
                    !format.equalsIgnoreCase(String.valueOf(Format.bmp)) &&
                    !format.equalsIgnoreCase(String.valueOf(Format.jpg))) {
                throw new UnsupportedFileFormatException("Unsupported file format. Only " +
                        getSupportedFormats() + " formats are allowed.");
            }
        }
    }

    private static String getSupportedFormats() {
        return Arrays.stream(Format.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}
