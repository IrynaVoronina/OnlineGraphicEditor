package com.example.backend.service.elements.template;

import com.example.backend.model.entities.elements.Element;

import java.awt.*;

public class RectangleDrawer extends LayerRenderer {


    public RectangleDrawer(Element element) {
        super(element);
    }

    @Override
    protected void renderLayer(Graphics graphics, Element element) {

        int x1 = (int) element.getCoordinates()[0];
        int y1 = (int) element.getCoordinates()[1];

        int x2 = (int) element.getCoordinates()[2];
        int y2 = (int) element.getCoordinates()[3];

        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        graphics.drawRect(x1, y1, width, height);
    }
}
