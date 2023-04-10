package ir.sharif.math.ap2023.supermario.controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ir.sharif.math.ap2023.supermario.logic.CharacterLoader;
import ir.sharif.math.ap2023.supermario.models.GameCharacter;
import ir.sharif.math.ap2023.supermario.models.State;
import ir.sharif.math.ap2023.supermario.models.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersistenceController {
    private static Gson gson;
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(GameCharacter.class, new JsonSerializer<GameCharacter>() {
            @Override
            public JsonElement serialize(GameCharacter gameCharacter, Type type, JsonSerializationContext jsonSerializationContext) {
                return new JsonPrimitive(gameCharacter.getName());
            }
        });

        gsonBuilder.registerTypeAdapter(GameCharacter.class, new JsonDeserializer<GameCharacter>() {

            @Override
            public GameCharacter deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return CharacterLoader.getCharacter(jsonElement.getAsString());
            }
        });

        gson = gsonBuilder.create();
    }

    private static String usersFilename = "data/users.json";

    public static void save() {
        File directoryToCreate = new File(usersFilename).getParentFile();
        if (directoryToCreate != null)
            directoryToCreate.mkdirs();

        List<User> allUsersCollection = State.getAllUsers();
        JsonWriter jsonWriter = null;
        try {
            FileWriter fileWriter = new FileWriter(usersFilename, false);
            jsonWriter = gson.newJsonWriter(fileWriter);
            gson.toJson(allUsersCollection, allUsersCollection.getClass(), jsonWriter);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load() {
        List<User> allUsersCollection = null;
        try {
            JsonReader jsonReader = gson.newJsonReader(new FileReader(usersFilename));
            TypeToken<List<User>> typeToken = new TypeToken<List<User>>(){};
            allUsersCollection = gson.fromJson(jsonReader, typeToken);
        } catch (FileNotFoundException e) {
            allUsersCollection = new ArrayList<>();
        } catch (JsonSyntaxException e) {
            allUsersCollection = new ArrayList<>();
        }

        if (allUsersCollection == null)
            allUsersCollection = new ArrayList<>();

        State.loadAllUsers(allUsersCollection);
    }



}
