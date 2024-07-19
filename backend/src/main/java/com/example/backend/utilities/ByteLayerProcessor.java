package com.example.backend.utilities;


import com.example.backend.model.entities.layer.Layer;
import com.example.backend.model.enums.Format;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ByteLayerProcessor {

    private final Layer layer;

    public ByteLayerProcessor(Layer layer) {
        this.layer = layer;
    }

    public BufferedImage getImageFromBytes() {
        try {
            return ImageIO.read(new ByteArrayInputStream(layer.getByteData()));
        } catch (IOException e) {
            throw new RuntimeException("Error reading image from bytes", e);
        }
    }

    public void setByteData(BufferedImage image, Format format) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, String.valueOf(format), baos);
            layer.setByteData(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error converting image to bytes", e);
        }
    }

}
