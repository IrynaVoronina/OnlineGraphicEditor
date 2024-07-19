package com.example.backend.dto.image;

import com.example.backend.model.enums.Format;
import com.example.backend.validation.annotation.EnumConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageSaveAsDto {

    @NotNull(message = "ID must be specified")
    Integer id;

    @NotBlank(message = "Title must be specified")
    @Size(max = 50, message = "Name should not exceed 50 characters")
    String name;

    @NotBlank(message = "Format must be specified")
    @EnumConstraint(enumClass = Format.class)
    String format;

    @NotBlank(message = "Location must be specified")
    @Size(max = 255, message = "Location should not exceed 255 characters")
    String location;

}