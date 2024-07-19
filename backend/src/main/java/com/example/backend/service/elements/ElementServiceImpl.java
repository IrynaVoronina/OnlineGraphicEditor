package com.example.backend.service.elements;

import com.example.backend.model.enums.GraphicPrimitiveElementType;
import com.example.backend.validation.exeption.ResourceNotFoundException;
import com.example.backend.model.entities.layer.Layer;
import com.example.backend.model.entities.layer.LayerCaretaker;
import com.example.backend.model.entities.layer.LayerMemento;
import com.example.backend.model.entities.elements.Element;
import com.example.backend.model.entities.elements.GraphicPrimitiveElement;
import com.example.backend.model.entities.elements.TextElement;
import com.example.backend.repository.ElementRepository;
import com.example.backend.repository.LayerRepository;
import com.example.backend.service.elements.template.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ElementServiceImpl implements ElementService {

    ElementRepository elementRepository;

    LayerRepository layerRepository;
    LayerCaretaker caretaker;

    @Override
    public Element getElementById(Integer id) {
        return getOrElseThrow(id);
    }

    private Element getOrElseThrow(Integer id) {
        return elementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Element with id %s does not exist", id)));
    }

    @Override
    @Transactional
    public Element addElement(Element element) {

        elementRepository.save(element);

        Layer layer = element.getLayer();
        LayerMemento memento = layer.saveToMemento();
        caretaker.setLayerMemento(memento);

        LayerRenderer renderer = getLayerRenderer(element);

        Layer renderedLayer = renderer.getRenderedLayer();

        layerRepository.save(renderedLayer);

        return element;
    }

    private LayerRenderer getLayerRenderer(Element element) {
        LayerRenderer renderer = null;
        if (element instanceof TextElement textElement) {
            renderer = new TexеDrawer(textElement);
        } else if (element instanceof GraphicPrimitiveElement primitiveElement) {
            GraphicPrimitiveElementType type = primitiveElement.getType();
            switch (type) {
                case circle -> renderer = new CircleDrawer(primitiveElement);
                case line -> renderer = new LineDrawer(primitiveElement);
                case rectangle -> renderer = new RectangleDrawer(primitiveElement);
                case triangle -> renderer = new TriangleDrawer(primitiveElement);
            }
        }
        return renderer;
    }


    @Override
    @Transactional
    public void deleteElement(Element element) {
        LayerMemento memento = caretaker.getLayerMemento();
        Layer layer = element.getLayer();
        layer.undoFromMemento(memento);

        elementRepository.delete(element);
        layerRepository.save(layer);
    }
}
