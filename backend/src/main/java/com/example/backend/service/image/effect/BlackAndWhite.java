package com.example.backend.service.image.effect;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class BlackAndWhite implements ImageEffect{
    @Override
    public BufferedImage apply(BufferedImage bufferedImage) {
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        BufferedImage grayscaleImage = op.filter(bufferedImage, null);

        BufferedImage resultImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resultImage.createGraphics();
        g2d.drawImage(grayscaleImage, 0, 0, null);
        g2d.dispose();
        return bufferedImage;
    }
}
