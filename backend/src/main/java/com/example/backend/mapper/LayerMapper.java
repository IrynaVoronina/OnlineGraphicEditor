package com.example.backend.mapper;

import com.example.backend.dto.layer.LayerResponseDto;

import com.example.backend.model.entities.layer.Layer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ElementMapper.class})
public interface LayerMapper {

    @Mapping(source = "image.id", target = "imageId")
    LayerResponseDto toResponseDto(Layer layer);

    @Mapping(source = "image.id", target = "imageId")
    List<LayerResponseDto> toResponseDtoList(Collection<Layer> layers);

}