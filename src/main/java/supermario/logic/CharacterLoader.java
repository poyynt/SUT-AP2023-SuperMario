package supermario.logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import supermario.models.GameCharacter;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class CharacterLoader {
    private static final Gson gson = new Gson();
    private static List<String> cachedListOfCharacters = null;
    private static final HashMap<String, GameCharacter> cachedCharacters = new HashMap<>();
    public static List<String> getListOfCharacters() {
        if (cachedListOfCharacters != null) {
            return cachedListOfCharacters;
        }
        try {
            InputStream resourceIS = CharacterLoader.class.getResourceAsStream("/characters/list.json");
            assert resourceIS != null;
            InputStreamReader resourceISReader = new InputStreamReader(resourceIS);

            TypeToken<List<String>> typeToken = new TypeToken<>() {
            };
            cachedListOfCharacters = gson.fromJson(resourceISReader, typeToken);
            return cachedListOfCharacters;
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameCharacter getCharacter(String name) {
        if (cachedCharacters.get(name) != null) {
            return cachedCharacters.get(name);
        }
        InputStream resourceIS = CharacterLoader.class.getResourceAsStream("/characters/" + name + ".json");
        assert resourceIS != null;
        InputStreamReader resourceISReader = new InputStreamReader(resourceIS);
        TypeToken<GameCharacter> typeToken = new TypeToken<>() {
        };
        cachedCharacters.put(name, gson.fromJson(resourceISReader, typeToken));
        return cachedCharacters.get(name);
    }

    public static GameCharacter getDefaultCharacter() {
        return getCharacter(getListOfCharacters().get(0));
    }
}
