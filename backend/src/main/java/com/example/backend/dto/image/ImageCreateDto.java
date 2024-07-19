package com.example.backend.dto.image;

import com.example.backend.model.enums.Format;
import com.example.backend.validation.annotation.EnumConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageCreateDto {

    @NotBlank(message = "Title must be specified")
    @Size(max = 50, message = "Name should not exceed 50 characters")
    String name;

    @Min(value = 1, message = "Width must be at least 1")
    int width;

    @Min(value = 1, message = "Height must be at least 1")
    int height;

    @NotBlank(message = "Format must be specified")
    @EnumConstraint(enumClass = Format.class)
    String format;

}
