package com.example.backend.model.entities.layer;


import com.example.backend.model.entities.image.Image;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import com.example.backend.model.entities.elements.Element;

import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LayerMemento {

    final Integer id;
    int position;
    Image image;
    byte[] byteData;
    List<Element> elements;

}



