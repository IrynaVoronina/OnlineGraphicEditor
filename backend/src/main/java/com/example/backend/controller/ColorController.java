package com.example.backend.controller;

import com.example.backend.dto.color.ColorRequestDto;
import com.example.backend.dto.color.ColorResponseDto;
import com.example.backend.mapper.ColorMapper;
import com.example.backend.model.entities.color.Color;
import com.example.backend.service.color.ColorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/colors")
public class ColorController {

    private ColorMapper colorMapper;
    private ColorService colorService;

    @PostMapping(value = "/setColor")
    public ResponseEntity<ColorResponseDto> setColor(@RequestBody @Valid ColorRequestDto colorRequestDto) {

        Color colorEntity = colorMapper.toEntity(colorRequestDto);
        Color color = colorService.createOrGetColor(colorEntity);
        ColorResponseDto dto = colorMapper.toDto(color);

        return ResponseEntity.ok().body(dto);
    }
}
