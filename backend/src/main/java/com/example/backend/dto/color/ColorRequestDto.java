package com.example.backend.dto.color;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ColorRequestDto {

    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Invalid hex code format")
    String hexCode;
}
