package supermario.utils.typeadapters;

import com.google.gson.*;
import supermario.models.EnemyObject;
import supermario.models.EnemyType;

import java.lang.reflect.Type;

public class EnemyObjectDeserializer implements JsonDeserializer<EnemyObject> {
    @Override
    public EnemyObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int x = jsonObject.get("x").getAsInt();
        int y = 20 - (jsonObject.get("y").getAsInt());
        EnemyType type = EnemyType.valueOf(jsonObject.get("type").getAsString());
        return new EnemyObject(x, y, type);
    }
}
