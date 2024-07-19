package com.example.backend.service.elements.template;

import com.example.backend.model.entities.elements.Element;

import com.example.backend.model.entities.layer.Layer;
import com.example.backend.model.enums.Format;
import com.example.backend.utilities.ByteLayerProcessor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Color.decode;


public abstract class LayerRenderer {

    private final Element element;
    private final ByteLayerProcessor byteLayerProcessor;


    public LayerRenderer(Element element) {
        this.element = element;
        byteLayerProcessor = new ByteLayerProcessor(element.getLayer());
    }

    public Layer getRenderedLayer() {

        Layer layer = element.getLayer();

        BufferedImage bufferedImage = byteLayerProcessor.getImageFromBytes();

        Graphics graphics = bufferedImage.getGraphics();

        graphics.setColor(decode(element.getColor().getHexCode()));

        renderLayer(graphics, element);

        graphics.dispose();

        byteLayerProcessor.setByteData(bufferedImage, Format.png);

        return layer;
    }


    protected abstract void renderLayer(Graphics graphics, Element element);


}

