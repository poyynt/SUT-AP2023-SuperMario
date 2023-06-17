package supermario.logic;

import com.google.gson.*;
import supermario.models.*;

import java.awt.*;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class MapHandler {
    public static SectionObject sectionObject;
    private static int loadedLevel = -1;
    private static int loadedSection = -1;

    private static final Gson gson;
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BlockObject.class, new JsonDeserializer<BlockObject>() {
            @Override
            public BlockObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                int x = jsonObject.get("x").getAsInt();
                int y = 20 - (jsonObject.get("y").getAsInt());
                BlockType type = BlockType.valueOf(jsonObject.get("type").getAsString());
                ItemType item = null;
                if (jsonObject.has("item"))
                    item = ItemType.valueOf(jsonObject.get("item").getAsString());
                System.err.println("DES " + x + " " + y + " " + type.name());
                return new BlockObject(x, y, type, item);
            }
        });

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
        sectionObject = gson.fromJson(
                new InputStreamReader(
                        MapHandler.class.getResourceAsStream("/map/" + level + "/" + section + ".json")
                ), SectionObject.class);
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

        for (BlockObject t: sectionObject.blocks) {
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteForTile(t),
                    t.x * 32 - state.getScreenX(),
                    t.y * 32,
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
