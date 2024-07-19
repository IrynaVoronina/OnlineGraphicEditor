package com.example.backend.mapper;

import com.example.backend.dto.image.ImageCreateDto;
import com.example.backend.dto.image.ImageResponseDto;
import com.example.backend.dto.image.ImageSaveAsDto;
import com.example.backend.model.entities.image.Image;
import com.example.backend.service.layer.LayerService;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {LayerMapper.class})
public interface ImageMapper {

    ImageResponseDto toDto(Image image);

    Image saveAsDtoToEntity(ImageSaveAsDto imageSaveAsDto);

    Image createDtoToEntity(ImageCreateDto imageCreateDto);
}