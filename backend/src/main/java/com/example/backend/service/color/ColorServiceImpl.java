package com.example.backend.service.color;

import com.example.backend.validation.exeption.ResourceNotFoundException;
import com.example.backend.model.entities.color.Color;
import com.example.backend.repository.ColorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    public Color getColorById(Integer id) {
        return getOrElseThrow(id);
    }

    private Color getOrElseThrow(Integer id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Color with id %s does not exist", id)));
    }

    @Override
    public Color createOrGetColor(Color color) {

        Color existingColor = colorRepository.findByHexCode(color.getHexCode());

        return Objects.requireNonNullElseGet(existingColor, () -> colorRepository.save(color));
    }
}
