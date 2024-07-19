package com.example.backend.service.layer;

import com.example.backend.dto.layer.LayerPositionDto;
import com.example.backend.model.entities.image.Image;
import com.example.backend.model.entities.layer.Layer;
import com.example.backend.validation.exeption.LayerDeleteException;

import java.util.List;

public interface LayerService {

    Layer getLayerById(Integer id);

    Layer addLayerForImage(Image image, byte[] byteData);

    Layer updateLayerPosition(Image image, LayerPositionDto layerOrder);

    void deleteLayer(Layer layer) throws LayerDeleteException;

    void deleteLayersByImage(Image image);

    List<Layer> getAllLayersForImage(Integer imageId);

    byte[] mergeLayers(Image image);

    Layer cloneById(Integer id);

}
