package supermario.logic;

import com.google.gson.*;
import supermario.models.*;
import supermario.utils.typeadapters.BlockObjectDeserializer;
import supermario.utils.typeadapters.EnemyObjectDeserializer;
import supermario.utils.typeadapters.PipeObjectDeserializer;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

public class MapHandler {
    public static FileObject fileObject;
    public static SectionObject sectionObject;
    private static int loadedLevel = -1;
    private static int loadedSection = -1;

    public static final Gson gson;
    private static File customMapFile;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(BlockObject.class, new BlockObjectDeserializer());
        gsonBuilder.registerTypeAdapter(EnemyObject.class, new EnemyObjectDeserializer());
        gsonBuilder.registerTypeAdapter(PipeObject.class, new PipeObjectDeserializer());

        gson = gsonBuilder.create();
    }

    public static void setCustomMapFile(File customMapFile) {
        MapHandler.customMapFile = customMapFile;
    }

    public static void loadMap() {
        if (customMapFile == null) {
            fileObject = gson.fromJson(
                    new InputStreamReader(
                            MapHandler.class.getResourceAsStream("/map/map.json")
                    ), FileObject.class);
        }
        else {
            try {
                fileObject = gson.fromJson(new FileReader(customMapFile), FileObject.class);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void loadSection() {
        if (fileObject == null)
            loadMap();
        int level = State.getCurrentGame().getLevel();
        int section = State.getCurrentGame().getSection();

        sectionObject = fileObject.levels.get(level - 1).sections.get(section - 1);
    }

    public static int lastSectionForLevel(int level) {
        return fileObject.levels.get(level - 1).sections.size();
    }

    public static int lastLevel() {
        return fileObject.levels.size();
    }

    public static BlockObject getBlockAt(int gridX, int gridY) {
        if (sectionObject == null)
            return null;
        if (sectionObject.blocks == null)
            return null;
        for (BlockObject t : sectionObject.blocks)
            if (t.x == gridX && t.y == gridY)
                return t;
        return null;
    }

    public static void paint(Graphics2D graphics2D) {
        for (int i = 0; i < 32; i++)
            for (int j = 0; j < 24; j++)
                graphics2D.drawImage(
                        SpriteLoader.loadSpriteWithName("block", "Sky"),
                        i * 32,
                        j * 32,
                        null
                );

        GameState state = State.getCurrentGame();

        for (int i = 0; i <= 32; i++) {
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteWithName("block", "Grass"),
                    i * 32 - state.getScreenX() % 32,
                    (20 - (-1)) * 32,
                    null
            );
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteWithName("block", "Soil"),
                    i * 32 - state.getScreenX() % 32,
                    (20 - (-2)) * 32,
                    null
            );
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteWithName("block", "Soil"),
                    i * 32 - state.getScreenX() % 32,
                    (20 - (-3)) * 32,
                    null
            );
        }

        for (BlockObject t : sectionObject.blocks) {
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteForBlock(t),
                    t.x * 32 - state.getScreenX(),
                    t.y * 32,
                    null
            );
        }

        for (PipeObject p : sectionObject.pipes) {
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteWithName("block", "Pipe"),
                    p.x * 32 - state.getScreenX(),
                    p.y * 32,
                    null
            );
        }

        for (ItemObject i : sectionObject.items) {
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteForItem(i),
                    i.getX() - state.getScreenX(),
                    i.getY(),
                    null
            );
        }

        for (EnemyObject e : sectionObject.enemies) {
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteForEnemy(e),
                    e.getX() - state.getScreenX(),
                    e.getY(),
                    null
            );
        }

        for (BulletObject b : sectionObject.bullets) {
            Color prevColor = graphics2D.getColor();
            graphics2D.setColor(Color.RED);
            graphics2D.fillOval(b.getX() - 4 - state.getScreenX(), b.getY() - 4, 8, 8);
            graphics2D.setColor(prevColor);
        }
    }

    public static void forceReload() {
        loadMap();
        loadedLevel = -1;
        loadedSection = -1;
        loadSection();
    }

    public static int getTime() {
        return sectionObject.time;
    }

}
