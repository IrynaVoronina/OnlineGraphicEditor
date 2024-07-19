package com.example.backend.service.image;

import com.example.backend.model.entities.image.Image;
import com.example.backend.model.entities.layer.Layer;
import com.example.backend.service.layer.LayerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class ImageStorageServiceImpl implements ImageStorageService {

    private final LayerService layerService;
    private final ImageService imageService;

    @Override
    @Transactional
    public Image saveImage(Image image) {

        byte[] byteData = layerService.mergeLayers(image);

        layerService.deleteLayersByImage(image);

        Layer layer = layerService.addLayerForImage(image, byteData);
        image.setLayers(List.of(layer));

        return image;
    }

    @Override
    @Transactional
    public Image saveImageAs(Image image){
        Image imageToSave = imageService.getImageById(image.getId());

        imageToSave.setName(image.getName());
        imageToSave.setFormat(image.getFormat());

        imageToSave.setLocation(image.getLocation());

        return saveImage(imageToSave);
    }
}