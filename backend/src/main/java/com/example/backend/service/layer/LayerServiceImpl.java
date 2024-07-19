package com.example.backend.service.layer;

import com.example.backend.dto.layer.LayerPositionDto;
import com.example.backend.model.enums.Format;
import com.example.backend.validation.exeption.LayerDeleteException;
import com.example.backend.validation.exeption.ResourceNotFoundException;
import com.example.backend.model.entities.image.Image;
import com.example.backend.model.entities.layer.Layer;
import com.example.backend.repository.LayerRepository;

import com.example.backend.utilities.ByteLayerProcessor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class LayerServiceImpl implements LayerService {

    private final LayerRepository layerRepository;

    @Override
    public Layer getLayerById(Integer id) {
        return getOrElseThrow(id);
    }

    private Layer getOrElseThrow(Integer id) {
        return layerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Layer with id %s does not exist", id)));
    }

    @Override
    public Layer addLayerForImage(Image image, byte[] byteData) {

        int maxLayerPositionForImage = layerRepository.findMaxLayerPositionForImage(image.getId());

        Layer layer = new Layer();
        layer.setPosition(maxLayerPositionForImage + 1);
        layer.setImage(image);

        if (byteData != null) {
            layer.setByteData(byteData);
        } else {
            BufferedImage newPngBufferedLayer = createNewPngBufferedLayer(image);

            ByteLayerProcessor byteLayerProcessor = new ByteLayerProcessor(layer);
            byteLayerProcessor.setByteData(newPngBufferedLayer, Format.png);
        }

        return layerRepository.save(layer);
    }

    private BufferedImage createNewPngBufferedLayer(Image image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage bufferedLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedLayer.createGraphics();
        g2d.setComposite(AlphaComposite.SrcOver);
        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();

        return bufferedLayer;
    }

    @Override
    @Transactional
    public Layer updateLayerPosition(Image image, LayerPositionDto layerPositionDto) {

        Layer layer = layerRepository.findById(layerPositionDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Layer not found"));

        int oldPosition = layer.getPosition();
        int newPosition = layerPositionDto.getNewPosition();

        int maxPosition = layerRepository.findMaxLayerPositionForImage(image.getId());

        if (oldPosition == newPosition) {
            return layer;
        }

        List<Layer> layers = layerRepository.findAllByImageIdOrderByPosition(image.getId());

        if (oldPosition < newPosition) {
            for (Layer l : layers) {
                if (l.getPosition() > oldPosition && l.getPosition() <= newPosition) {
                    l.setPosition(l.getPosition() - 1);
                }
            }
        } else {
            for (Layer l : layers) {
                if (l.getPosition() < oldPosition && l.getPosition() >= newPosition) {
                    l.setPosition(l.getPosition() + 1);
                }
            }
        }

        if (newPosition > maxPosition) {
            newPosition = maxPosition;
        }

        layer.setPosition(newPosition);
        layerRepository.saveAll(layers);
        return layer;
    }


    @Override
    @Transactional
    public Layer cloneById(Integer id) {
        Layer layer = getOrElseThrow(id);
        Layer clone = layer.clone();
        clone.setPosition(layerRepository.findMaxLayerPositionForImage(clone.getImage().getId()) + 1);
        layerRepository.save(clone);
        return clone;
    }

    @Override
    @Transactional
    public void deleteLayer(Layer layer) throws LayerDeleteException {
        Image image = layer.getImage();

        /*if (image.getLayers().size() == 1) {
            throw new LayerDeleteException("It is not possible to delete this layer, " +
                    "because it is the only one for the image");
        }*/

        layerRepository.delete(layer);
        updateLayerPositionsAfterDelete(image.getId());
    }

    private void updateLayerPositionsAfterDelete(Integer imageId) {
        List<Layer> allLayersForImage = getAllLayersForImage(imageId);

        int i = 1;
        for (Layer layer : allLayersForImage) {
            layer.setPosition(i++);
        }
    }

    @Override
    public void deleteLayersByImage(Image image) {
        layerRepository.deleteLayersByImage(image);
    }

    @Override
    public List<Layer> getAllLayersForImage(Integer imageId) {
        return layerRepository.findAllByImageIdOrderByPosition(imageId);
    }

    @Override
    public byte[] mergeLayers(Image image) {
        List<Layer> layers = image.getLayers();
        if (layers.isEmpty()) {
            throw new RuntimeException("No layers provided for merging.");
        }

        int height = layers.get(0).getImage().getHeight();
        int width = layers.get(0).getImage().getWidth();

        BufferedImage fullImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = fullImage.createGraphics();
        for (Layer layer : layers) {
            ByteLayerProcessor byteLayerProcessor = new ByteLayerProcessor(layer);
            BufferedImage layerImageFromBytes = byteLayerProcessor.getImageFromBytes();
            g2d.setComposite(AlphaComposite.SrcOver);
            g2d.drawImage(layerImageFromBytes, 0, 0, null);
        }

        g2d.dispose();

        return BufferedImageToBytes(fullImage, String.valueOf(image.getFormat()));
    }


    private byte[] BufferedImageToBytes(BufferedImage bufferedImage, String format) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage newImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            bufferedImage = newImage;
            ImageIO.write(bufferedImage, format, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error converting bufferedImage to bytes.", e);
        }
    }
}
