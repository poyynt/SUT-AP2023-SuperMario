package ir.sharif.math.ap2023.supermario.logic;

import com.google.gson.Gson;
import ir.sharif.math.ap2023.supermario.models.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;

public class MapHandler {
    public static TileMap tileMap;
    private static int loadedLevel = -1;
    private static int loadedSection = -1;

    private static final Gson gson = new Gson();

    public static void loadSection() {
        int level = State.getCurrentGame().getLevel();
        int section = State.getCurrentGame().getSection();
        if (level == loadedLevel && section == loadedSection)
            return;
        loadedLevel = level;
        loadedSection = section;
        try {
            //noinspection DataFlowIssue
            tileMap = gson.fromJson(gson.newJsonReader(
                    new FileReader(new File(
                            MapHandler.class.getResource("/map/" + level + "/" + section + ".json").toURI())
                    )), TileMap.class);
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Tile getTileAt(int gridX, int gridY) {
        if (tileMap == null)
            return null;
        if (tileMap.tiles == null)
            return null;
        for (Tile t: tileMap.tiles)
            if (t.x == gridX && t.y == gridY)
                return t;
        return null;
    }

    public static void paint(Graphics2D graphics2D) {
        for (int i = 0; i < 12; i++)
            for (int j = 0; j < 8; j++)
                graphics2D.drawImage(
                        SpriteLoader.loadSpriteForTile(new Tile(0, 0, "Sky")),
                        i * 64,
                        j * 64,
                        null
                );

        GameState state = State.getCurrentGame();

        for (Tile t: tileMap.tiles) {
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteForTile(t),
                    t.x * 64 - state.getScreenX(),
                    t.y * 64,
                    null
            );
        }
    }

    public static void forceReload() {
        loadedLevel = -1;
        loadedSection = -1;
        loadSection();
    }

    public static int getTime() {
        return tileMap.time;
    }
}
