package com.example.backend.service.elements.template;


import com.example.backend.model.entities.elements.Element;
import com.example.backend.model.entities.elements.TextElement;

import java.awt.*;

public class TexеDrawer extends LayerRenderer {

    public TexеDrawer(Element element) {
        super(element);
    }

    @Override
    protected void renderLayer(Graphics graphics, Element element) {
        if (element instanceof TextElement textElement) {

            Font font = new Font(textElement.getFontName(), Font.PLAIN, textElement.getSize());
            graphics.setFont(font);

            double[] coordinates = textElement.getCoordinates();
            double x = coordinates[0];
            double y = coordinates[1];

            graphics.drawString(textElement.getText(), (int) x, (int) y);
        }
    }
}
