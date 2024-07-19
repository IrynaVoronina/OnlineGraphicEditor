package com.example.backend.dto.layer;

import com.example.backend.dto.elements.ElementResponseDto;

import com.example.backend.model.entities.image.Image;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LayerResponseDto {

    Integer id;
    int position;
    Integer imageId;
    byte[] byteData;
    List<ElementResponseDto> elements;

}

