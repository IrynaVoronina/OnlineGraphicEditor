package com.example.backend.model.entities.elements;


import com.example.backend.model.entities.layer.Layer;
import com.example.backend.model.entities.color.Color;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    double[] coordinates;

    @ManyToOne
    @JoinColumn(name = "colorId")
    Color color;

    @ManyToOne
    @JoinColumn(name = "layerId")
    Layer layer;

    public abstract Element clone();
}




