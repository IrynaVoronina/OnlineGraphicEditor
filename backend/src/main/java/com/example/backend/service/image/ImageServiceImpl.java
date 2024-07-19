package com.example.backend.service.image;

import com.example.backend.service.image.effect.ImageEffect;
import com.example.backend.service.image.effect.ImageEffectFactory;
import com.example.backend.utilities.FileOperation;
import com.example.backend.validation.validator.FormatValidator;
import com.example.backend.validation.exeption.ResourceNotFoundException;
import com.example.backend.model.entities.image.Image;
import com.example.backend.model.entities.layer.Layer;
import com.example.backend.model.enums.Format;
import com.example.backend.service.image.effect.VisualEffect;
import com.example.backend.repository.ImageRepository;

import com.example.backend.repository.LayerRepository;
import com.example.backend.service.layer.LayerService;
import com.example.backend.utilities.ByteLayerProcessor;
import com.example.backend.validation.exeption.UnsupportedFileFormatException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;
import java.util.List;


@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final LayerService layerService;
    private final LayerRepository layerRepository;

    @Override
    public Image getImageById(Integer id) {
        return getOrElseThrow(id);
    }

    private Image getOrElseThrow(Integer imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Image with id %s does not exist", imageId)));
    }

    @Override
    @Transactional
    public Image createImage(Image image) {

        Image newImage = new Image();
        newImage.setName(image.getName());
        newImage.setWidth(image.getWidth());
        newImage.setHeight(image.getHeight());
        newImage.setFormat(image.getFormat());

        Layer layer = layerService.addLayerForImage(newImage, null);

        newImage.setLayers(List.of(layer));

        imageRepository.save(newImage);

        return newImage;
    }


    @Override
    @Transactional
    public Image uploadImage(MultipartFile file) throws IOException, UnsupportedFileFormatException {

        if (file == null) {
            throw new NullPointerException("file is null");
        }

        FileOperation fileOperation = new FileOperation(file);

        String format = fileOperation.getFileFormat();
        FormatValidator.validate(format);

        byte[] fileData = file.getBytes();

        Image image = new Image();
        image.setName(fileOperation.getNameWithoutExtension());

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        image.setWidth(width);
        image.setHeight(height);


        image.setFormat(Format.valueOf(format.toLowerCase()));

        image.setLocation(null);

        Layer layer = layerService.addLayerForImage(image, fileData);
        image.setLayers(List.of(layer));

        image = imageRepository.save(image);

        return image;
    }


    @Override
    public Image applyVisualEffect(Image image, String effect) {
        VisualEffect visualEffect = VisualEffect.valueOf(effect.toLowerCase());
        ImageEffect imageEffect = ImageEffectFactory.getEffect(visualEffect);

        for (Layer layer : image.getLayers()) {
            ByteLayerProcessor byteLayerProcessor = new ByteLayerProcessor(layer);
            BufferedImage bufferedImage = byteLayerProcessor.getImageFromBytes();

            BufferedImage newImage = imageEffect.apply(bufferedImage);

            byteLayerProcessor.setByteData(newImage, Format.png);
            layerRepository.save(layer);
        }
        imageRepository.save(image);
        return image;
    }

    @Override
    @Transactional
    public void deleteImage(Image image) {
        imageRepository.delete(image);
    }
}

