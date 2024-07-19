package com.example.backend.controller;

import com.example.backend.dto.layer.LayerPositionDto;
import com.example.backend.dto.layer.LayerResponseDto;

import com.example.backend.mapper.LayerMapper;
import com.example.backend.model.entities.image.Image;
import com.example.backend.model.entities.layer.Layer;
import com.example.backend.service.image.ImageService;
import com.example.backend.service.layer.LayerService;
import com.example.backend.validation.exeption.LayerDeleteException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/layers")
public class LayerController {

    private LayerService layerService;
    private LayerMapper layerMapper;
    private ImageService imageService;


    @GetMapping("/{layerId}")
    public ResponseEntity<LayerResponseDto> getLayerById(@PathVariable Integer layerId) {
        return ResponseEntity.ok().body(layerMapper.toResponseDto(layerService.getLayerById(layerId)));
    }


    @GetMapping("/allLayers")
    public ResponseEntity<List<LayerResponseDto>> getAllLayersForImageWithPositions(@RequestParam("imageId") Integer imageId) {
        List<Layer> layerList = layerService.getAllLayersForImage(imageId);
        List<LayerResponseDto> dtoList = layerMapper.toResponseDtoList(layerList);
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/addLayer")
    public ResponseEntity<LayerResponseDto> addLayer(@RequestParam("imageId") Integer imageId) {
        Layer addedLayerForImage = layerService.addLayerForImage(imageService.getImageById(imageId), null);
        return ResponseEntity.ok().body(layerMapper.toResponseDto(addedLayerForImage));
    }
    @PostMapping("/clone")
    public ResponseEntity<LayerResponseDto> cloneLayer(@RequestParam("layerId") Integer layerId) {
        return ResponseEntity.ok().body(layerMapper.toResponseDto(layerService.cloneById(layerId)));
    }

    @PutMapping("/updatePosition")
    public ResponseEntity<LayerResponseDto> updatePosition(@RequestBody @Valid LayerPositionDto layerPositionDto,
                                                           @RequestParam("imageId") Integer imageId) {
        Image image = imageService.getImageById(imageId);
        Layer layer = layerService.updateLayerPosition(image, layerPositionDto);
        return ResponseEntity.ok().body(layerMapper.toResponseDto(layer));
    }

    @DeleteMapping("/{layerId}")
    public ResponseEntity<Void> deleteLayer(@PathVariable Integer layerId) throws LayerDeleteException {
        Layer layer = layerService.getLayerById(layerId);
        layerService.deleteLayer(layer);
        return ResponseEntity.ok().build();
    }
}



