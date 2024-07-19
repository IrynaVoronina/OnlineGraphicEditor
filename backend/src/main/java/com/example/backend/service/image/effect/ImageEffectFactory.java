package com.example.backend.service.image.effect;


public class ImageEffectFactory {

    public static ImageEffect getEffect(VisualEffect visualEffect) {
        return switch (visualEffect) {
            case blackAndWhite -> new BlackAndWhite();
            case infrared -> new Infrared();
            case sepia -> new Sepia();
            case redColored -> new RedColored();
            default -> throw new IllegalArgumentException("There is no such effect");
        };
    }
}
