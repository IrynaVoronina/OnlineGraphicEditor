package com.example.backend.service.color;

import com.example.backend.model.entities.color.Color;

public interface ColorService {

    Color getColorById(Integer id);

    Color createOrGetColor(Color color);
}
