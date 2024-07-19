package com.example.backend.service.image.effect;

import java.awt.image.BufferedImage;

public class RedColored implements ImageEffect{
    @Override
    public BufferedImage apply(BufferedImage bufferedImage) {
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {

                int rgb = bufferedImage.getRGB(x, y);

                int alpha = (rgb >> 24) & 0xff;
                int red = (rgb >> 16) & 0xff;

                rgb = (alpha << 24) | (red << 16) | (0 << 8) | 0;

                bufferedImage.setRGB(x, y, rgb);
            }
        }
        return bufferedImage;
    }
}
