package com.example.backend.service.elements.template;

import com.example.backend.model.entities.elements.Element;

import java.awt.*;

public class LineDrawer extends LayerRenderer {


    public LineDrawer(Element element) {
        super(element);
    }

    @Override
    protected void renderLayer(Graphics graphics, Element element) {

        int x1 = (int) element.getCoordinates()[0];
        int y1 = (int) element.getCoordinates()[1];

        int x2 = (int) element.getCoordinates()[2];
        int y2 = (int) element.getCoordinates()[3];

        graphics.drawLine(x1, y1, x2, y2);
    }
}


