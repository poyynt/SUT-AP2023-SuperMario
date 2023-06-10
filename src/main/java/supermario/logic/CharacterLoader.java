package supermario.logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import supermario.models.GameCharacter;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
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
            //noinspection DataFlowIssue
            URI resourceURI = CharacterLoader.class.getResource("/characters/list.json").toURI();
            File resourceFile = new File(resourceURI);
            FileReader resourceFileReader = new FileReader(resourceFile);
            TypeToken<List<String>> typeToken = new TypeToken<>() {
            };
            cachedListOfCharacters = gson.fromJson(resourceFileReader, typeToken);
            return cachedListOfCharacters;
        } catch (URISyntaxException | FileNotFoundException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameCharacter getCharacter(String name) {
        if (cachedCharacters.get(name) != null) {
            return cachedCharacters.get(name);
        }
        try {
            //noinspection DataFlowIssue
            URI resourceURI = CharacterLoader.class.getResource("/characters/" + name + ".json").toURI();
            File resourceFile = new File(resourceURI);
            FileReader resourceFileReader = new FileReader(resourceFile);
            TypeToken<GameCharacter> typeToken = new TypeToken<>() {
            };
            cachedCharacters.put(name, gson.fromJson(resourceFileReader, typeToken));
            return cachedCharacters.get(name);
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameCharacter getDefaultCharacter() {
        return getCharacter(getListOfCharacters().get(0));
    }
}
