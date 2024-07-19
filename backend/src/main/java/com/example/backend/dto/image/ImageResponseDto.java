package com.example.backend.dto.image;

import com.example.backend.dto.layer.LayerResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageResponseDto {

    Integer id;
    String name;
    int width;
    int height;
    String format;
    String location;
    List<LayerResponseDto> layers;

}