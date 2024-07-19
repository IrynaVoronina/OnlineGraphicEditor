package com.example.backend.dto.elements;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ElementDetailsDto {

    @NotEmpty(message = "Coordinates must be specified")
    @Size(min = 2, message = "Coordinates must contain at least 2 values")
    double[] coordinates;

    @NotNull(message = "Color ID must be specified")
    Integer colorId;

    @NotNull(message = "Layer ID must be specified")
    Integer layerId;

}

