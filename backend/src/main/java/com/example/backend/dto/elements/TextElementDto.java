package com.example.backend.dto.elements;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TextElementDto extends ElementDetailsDto {

    @NotBlank(message = "Font name must be specified")
    String fontName;

    @Positive(message = "Size must be a positive number")
    int size;

    @NotBlank(message = "Text must be specified")
    String text;

}