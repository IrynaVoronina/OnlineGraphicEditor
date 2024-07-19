package com.example.backend.dto.layer;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.NotNull;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LayerPositionDto {

    @NotNull(message = "ID must be specified")
    Integer id;

    @Min(value = 1, message = "New position must be at least 1")
    int newPosition;
}
