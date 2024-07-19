package com.example.backend.model.entities.elements;

import com.example.backend.model.enums.GraphicPrimitiveElementType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@PrimaryKeyJoinColumn(name = "elementId")
public class GraphicPrimitiveElement extends Element {

    @Enumerated(EnumType.STRING)
    GraphicPrimitiveElementType type;

    public GraphicPrimitiveElement(GraphicPrimitiveElement graphicPrimitiveElement) {
        if (graphicPrimitiveElement != null) {
            this.type = graphicPrimitiveElement.type;
            super.coordinates = graphicPrimitiveElement.getCoordinates();
            super.color = graphicPrimitiveElement.color;
        }
    }

    public Element clone() {
        return new GraphicPrimitiveElement(this);
    }
}




