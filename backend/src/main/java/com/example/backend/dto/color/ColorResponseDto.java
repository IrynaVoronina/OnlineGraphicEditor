package com.example.backend.dto.color;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ColorResponseDto {

    Integer id;
    String hexCode;
}


