package com.example.backend.service.image.effect;

import java.awt.image.BufferedImage;

public interface ImageEffect {
    BufferedImage apply(BufferedImage image);
}
