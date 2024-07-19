package com.example.backend.model.entities.layer;


import org.springframework.stereotype.Component;

@Component
public class LayerCaretaker {

    private LayerMemento layerMemento;

    public LayerMemento getLayerMemento() {
        return layerMemento;
    }

    public void setLayerMemento(LayerMemento layerMemento) {
        this.layerMemento = layerMemento;
    }

}

