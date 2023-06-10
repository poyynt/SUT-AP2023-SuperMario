package supermario.logic;

import supermario.models.GameCharacter;
import supermario.models.Item;
import supermario.models.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class SpriteLoader {

    private static final Map<String, BufferedImage> cache = new HashMap<>();

    public static Image loadSpriteWithName(String category, String spriteName) {
        try {
            if (cache.get(category + "/" + spriteName) == null) {
                //noinspection DataFlowIssue
                File directory = new File(SpriteLoader.class.getResource("/sprites/" + category + "/").toURI());
                //noinspection DataFlowIssue
                String filenameWithExtension = directory.list(
                        (dir, name) -> name.startsWith(spriteName + ".")
                )[0];
                //noinspection DataFlowIssue
                cache.put(
                        category + "/" + spriteName,
                        ImageIO.read(
                                SpriteLoader.class.getResource(
                                        "/sprites/" + category + "/" + filenameWithExtension)
                        )
                );
            }

            return cache.get(category + "/" + spriteName);
        }
        catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image loadSpriteForCharacter(GameCharacter c) {
        return loadSpriteWithName("character", c.getName());
    }

    public static Image loadSpriteForTile(Tile t) {
        return loadSpriteWithName("block", t.name);
    }

    public static Image loadSpriteForItem(Item i) {
        return loadSpriteWithName("item", i.name);
    }
}
