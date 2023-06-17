package supermario.utils.typeadapters;

import com.google.gson.*;
import supermario.models.BlockObject;
import supermario.models.BlockType;
import supermario.models.ItemType;

import java.lang.reflect.Type;

public class BlockObjectDeserializer implements JsonDeserializer<BlockObject> {
    @Override
    public BlockObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int x = jsonObject.get("x").getAsInt();
        int y = 20 - (jsonObject.get("y").getAsInt());
        BlockType type = BlockType.valueOf(jsonObject.get("type").getAsString());
        ItemType item = null;
        if (jsonObject.has("item"))
            item = ItemType.valueOf(jsonObject.get("item").getAsString());
        return new BlockObject(x, y, type, item);
    }
}
