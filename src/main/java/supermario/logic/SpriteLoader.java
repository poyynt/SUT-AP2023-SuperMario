package supermario.logic;

import supermario.models.GameCharacter;
import supermario.models.BlockObject;
import supermario.models.ItemObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SpriteLoader {
    private static final String[] extensions = {".png", ".gif"};

    private static final Map<String, BufferedImage> cache = new HashMap<>();

    public static Image loadSpriteWithName(String category, String spriteName) {
        try {
            if (cache.get(category + "/" + spriteName) == null) {
                for (String extension: extensions) {
                    InputStream fileIS = SpriteLoader.class.getResourceAsStream("/sprites/" + category + "/" + spriteName + extension);
                    if (fileIS != null) {
                        cache.put(
                                category + "/" + spriteName,
                                ImageIO.read(fileIS)
                        );
                        return cache.get(category + "/" + spriteName);
                    }
                }
            }
            else
                return cache.get(category + "/" + spriteName);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("No sprite with name " + spriteName + " found.");
    }

    public static Image loadSpriteForCharacter(GameCharacter c, int state) {
        return loadSpriteWithName("character." + state, c.getName());
    }

    public static Image loadSpriteForBlock(BlockObject t) {
        return loadSpriteWithName("block", t.type.name());
    }

    public static Image loadSpriteForItem(ItemObject t) {
        return loadSpriteWithName("item", t.type.name());
    }
}
