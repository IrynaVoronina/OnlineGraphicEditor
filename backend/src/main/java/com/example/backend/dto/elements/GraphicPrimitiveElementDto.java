package com.example.backend.dto.elements;


import com.example.backend.model.enums.GraphicPrimitiveElementType;
import com.example.backend.validation.annotation.EnumConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GraphicPrimitiveElementDto extends ElementDetailsDto {

    @NotBlank(message = "Type must be specified")
    @EnumConstraint(enumClass = GraphicPrimitiveElementType.class)
    String type;

}
