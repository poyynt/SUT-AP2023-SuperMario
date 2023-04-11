package ir.sharif.math.ap2023.supermario.logic;

import ir.sharif.math.ap2023.supermario.models.GameCharacter;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SpriteLoader {

    private static Map<String, BufferedImage> cache = new HashMap<>();

    public static Image loadSpriteWithName(String category, String spriteName) {
        try {
            if (cache.get(spriteName) == null) {
                File dir = new File(SpriteLoader.class.getResource("/sprites/character/").toURI());
                String filenameWithExtension = dir.list(
                        (dir1, name) -> name.startsWith(spriteName + ".")
                )[0];
                cache.put(
                        spriteName,
                        ImageIO.read(
                                SpriteLoader.class.getResource(
                                        "/sprites/" + category + "/" + filenameWithExtension)
                        )
                );
            }

            return cache.get(spriteName);
        }
        catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image loadSpriteForCharacter(GameCharacter c) {
        return loadSpriteWithName("character", c.getName());
    }
}
