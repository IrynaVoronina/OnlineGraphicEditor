package com.example.backend.mapper;

import com.example.backend.dto.elements.ElementResponseDto;
import com.example.backend.dto.elements.GraphicPrimitiveElementDto;
import com.example.backend.dto.elements.TextElementDto;
import com.example.backend.model.entities.elements.Element;
import com.example.backend.model.entities.elements.GraphicPrimitiveElement;
import com.example.backend.model.entities.elements.TextElement;
import com.example.backend.service.color.ColorService;
import com.example.backend.service.layer.LayerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ElementMapper {

    @Autowired
    protected LayerService layerService;

    @Autowired
    protected ColorService colorService;

    @Mapping(source = "layer.id", target = "layerId")
    @Mapping(source = "color.id", target = "colorId")
    public abstract ElementResponseDto toDto(Element entity);

    @Mapping(target = "layer", expression = "java(this.layerService.getLayerById(dto.getLayerId()))")
    @Mapping(target = "color", expression = "java(this.colorService.getColorById(dto.getColorId()))")
    public abstract TextElement toTextElementEntity(TextElementDto dto);

    @Mapping(target = "layer", expression = "java(this.layerService.getLayerById(dto.getLayerId()))")
    @Mapping(target = "color", expression = "java(this.colorService.getColorById(dto.getColorId()))")
    public abstract GraphicPrimitiveElement toGraphicPrimitiveElementEntity(GraphicPrimitiveElementDto dto);

}
