package ir.sharif.math.ap2023.supermario.logic;

import ir.sharif.math.ap2023.supermario.models.GameCharacter;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpriteLoader {

    private static Map<String, BufferedImage> cache = new HashMap<>();

    public static Image loadSpriteWithName(String spriteName) {
        try {
            if (cache.get(spriteName) == null) {
                cache.put(
                        spriteName,
                        ImageIO.read(
                                SpriteLoader.class.getResource("/sprites/characters/" + spriteName + ".png")
                        )
                );
            }

            return cache.get(spriteName);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image loadSpriteForCharacter(GameCharacter c) {
        return loadSpriteWithName(c.getName());
    }
}
