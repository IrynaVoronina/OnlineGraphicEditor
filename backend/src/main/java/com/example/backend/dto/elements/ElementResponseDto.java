
package com.example.backend.dto.elements;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElementResponseDto {

    Integer id;
    double[] coordinates;
    Integer colorId;
    Integer layerId;

}

