package ir.sharif.math.ap2023.supermario.models;

import ir.sharif.math.ap2023.supermario.logic.CharacterLoader;
import ir.sharif.math.ap2023.supermario.utils.HashedPassword;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private final HashedPassword hashedPassword;
    private int coins = 0;
    private long highScore = 0;

    private static List<GameCharacter> defaultOwnedCharacters = new ArrayList<>();
    static {
        defaultOwnedCharacters.add(CharacterLoader.getDefaultCharacter());
    }

    private List<GameCharacter> ownedCharacters = new ArrayList<>(defaultOwnedCharacters);

    private GameCharacter currentCharacter = CharacterLoader.getDefaultCharacter();

    private GameState[] slots = new GameState[3];

    public User(String username, String password) {
        this.username = username;
        this.hashedPassword = new HashedPassword(password);
//        ownedCharacters = new ArrayList<>();
//        ownedCharacters.add(CharacterLoader.getDefaultCharacter());
    }

    public String getUsername() {
        return username;
    }

    public boolean validatePassword(String password) {
        return this.hashedPassword.validate(password);
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public long getHighScore() {
        return highScore;
    }

    public void setHighScore(long highScore) {
        this.highScore = highScore;
    }

    public List<GameCharacter> getOwnedCharacters() {
        return ownedCharacters;
    }

    public void addOwnedCharacter(GameCharacter character) {
        this.ownedCharacters.add(character);
    }

    public GameCharacter getCurrentCharacter() {
        return currentCharacter;
    }

    public void setCurrentCharacter(GameCharacter currentCharacter) {
        this.currentCharacter = currentCharacter;
    }

    public GameState[] getSlots() {
        return slots;
    }
}
