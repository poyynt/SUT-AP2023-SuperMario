package ir.sharif.math.ap2023.supermario.logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ir.sharif.math.ap2023.supermario.models.GameCharacter;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class CharacterLoader {
    private static Gson gson = new Gson();
    public static List<String> getListOfCharacters() {
        try {
            URI resourceURI = CharacterLoader.class.getResource("/characters/list.json").toURI();
            File resourceFile = new File(resourceURI);
            FileReader resourceFileReader = new FileReader(resourceFile);
            TypeToken<List<String>> typeToken = new TypeToken<List<String>>(){};
            List<String> listOfCharacters = gson.fromJson(resourceFileReader, typeToken);
            return listOfCharacters;
        } catch (URISyntaxException | FileNotFoundException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameCharacter getCharacter(String name) {
        try {
            URI resourceURI = CharacterLoader.class.getResource("/characters/" + name + ".json").toURI();
            File resourceFile = new File(resourceURI);
            FileReader resourceFileReader = new FileReader(resourceFile);
            TypeToken<GameCharacter> typeToken = new TypeToken<GameCharacter>(){};
            GameCharacter gameCharacter = gson.fromJson(resourceFileReader, typeToken);
            return gameCharacter;
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameCharacter getDefaultCharacter() {
        return getCharacter(getListOfCharacters().get(0));
    }
}
