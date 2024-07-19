package com.example.backend.service.elements;

import com.example.backend.model.entities.elements.Element;

public interface ElementService {

    Element getElementById(Integer id);

    Element addElement(Element element);

    void deleteElement(Element element);
}
