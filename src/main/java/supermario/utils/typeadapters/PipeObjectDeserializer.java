package supermario.utils.typeadapters;

import com.google.gson.*;
import supermario.logic.MapHandler;
import supermario.models.*;

import java.lang.reflect.Type;

public class PipeObjectDeserializer implements JsonDeserializer<PipeObject> {
    @Override
    public PipeObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int x = jsonObject.get("x").getAsInt();
        int y = 20 - (jsonObject.get("y").getAsInt());
        PipeType type = PipeType.valueOf(jsonObject.get("type").getAsString());
        SectionObject section = null;
        if (jsonObject.has("section"))
            section = MapHandler.gson.fromJson(jsonObject.get("section"), SectionObject.class);
        boolean activated = false;
        if (jsonObject.has("activated"))
            activated = jsonObject.get("activated").getAsBoolean();
        return new PipeObject(x, y, type, section, activated);
    }
}
