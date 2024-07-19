package com.example.backend.mapper;

import com.example.backend.dto.color.ColorRequestDto;
import com.example.backend.dto.color.ColorResponseDto;
import com.example.backend.model.entities.color.Color;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ElementMapper.class})
public interface ColorMapper {

    ColorResponseDto toDto(Color color);

    Color toEntity(ColorRequestDto colorRequestDto);
}
