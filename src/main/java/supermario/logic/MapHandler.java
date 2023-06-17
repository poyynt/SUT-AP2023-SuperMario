package supermario.logic;

import com.google.gson.*;
import supermario.models.*;
import supermario.utils.typeadapters.BlockObjectDeserializer;
import supermario.utils.typeadapters.EnemyObjectDeserializer;
import supermario.utils.typeadapters.PipeObjectDeserializer;

import java.awt.*;
import java.io.InputStreamReader;

public class MapHandler {
    public static SectionObject sectionObject;
    private static int loadedLevel = -1;
    private static int loadedSection = -1;

    private static final Gson gson;
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(BlockObject.class, new BlockObjectDeserializer());
        gsonBuilder.registerTypeAdapter(EnemyObject.class, new EnemyObjectDeserializer());
        gsonBuilder.registerTypeAdapter(PipeObject.class, new PipeObjectDeserializer());

        gson = gsonBuilder.create();
    }

    public static void loadSection() {
        int level = State.getCurrentGame().getLevel();
        int section = State.getCurrentGame().getSection();
        if (level == loadedLevel && section == loadedSection)
            return;
        loadedLevel = level;
        loadedSection = section;
        //noinspection DataFlowIssue
        LevelObject levelObject = gson.fromJson(
                new InputStreamReader(
                        MapHandler.class.getResourceAsStream("/map/" + level + ".json")
                ), LevelObject.class);

        sectionObject = levelObject.sections.get(section - 1);
    }

    public static BlockObject getTileAt(int gridX, int gridY) {
        if (sectionObject == null)
            return null;
        if (sectionObject.blocks == null)
            return null;
        for (BlockObject t: sectionObject.blocks)
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

        for (BlockObject t: sectionObject.blocks) {
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteForTile(t),
                    t.x * 32 - state.getScreenX(),
                    t.y * 32,
                    null
            );
        }

        for (PipeObject p: sectionObject.pipes) {
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteWithName("block", "Pipe"),
                    p.x * 32 - state.getScreenX(),
                    p.y * 32,
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
        return sectionObject.time;
    }

}
