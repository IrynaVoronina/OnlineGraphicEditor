package com.example.backend.controller;

import com.example.backend.dto.elements.ElementResponseDto;
import com.example.backend.dto.elements.GraphicPrimitiveElementDto;
import com.example.backend.dto.elements.TextElementDto;

import com.example.backend.mapper.ElementMapper;
import com.example.backend.model.entities.elements.Element;
import com.example.backend.model.entities.elements.GraphicPrimitiveElement;
import com.example.backend.model.entities.elements.TextElement;
import com.example.backend.service.elements.ElementService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/elements")
public class ElementController {

    private ElementMapper elementMapper;
    private ElementService elementService;


    @PutMapping(value = "/addText")
    public ResponseEntity<ElementResponseDto> addText(@RequestBody @Valid TextElementDto textElementDto) {

        TextElement textElementEntity = elementMapper.toTextElementEntity(textElementDto);
        Element addedElement = elementService.addElement(textElementEntity);
        ElementResponseDto elementResponseDto = elementMapper.toDto(addedElement);

        return ResponseEntity.ok().body(elementResponseDto);
    }


    @PutMapping(value = "/addGraphicPrimitive")
    public ResponseEntity<ElementResponseDto> addGraphicPrimitive(
            @RequestBody @Valid GraphicPrimitiveElementDto graphicPrimitiveElementDto) {

        GraphicPrimitiveElement entity = elementMapper.toGraphicPrimitiveElementEntity(graphicPrimitiveElementDto);
        Element addedElement = elementService.addElement(entity);
        ElementResponseDto elementResponseDto = elementMapper.toDto(addedElement);

        return ResponseEntity.ok().body(elementResponseDto);
    }


    @DeleteMapping("/{elementId}")
    public ResponseEntity<Void> deleteElement(@PathVariable Integer elementId) {

        elementService.deleteElement(elementService.getElementById(elementId));

        return ResponseEntity.ok().build();
    }
}




