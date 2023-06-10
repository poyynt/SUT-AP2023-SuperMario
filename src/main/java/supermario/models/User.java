package supermario.models;

import supermario.logic.CharacterLoader;
import supermario.utils.HashedPassword;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private final HashedPassword hashedPassword;
    private int coins = 0;
    private long highScore = 0;

    private static final List<GameCharacter> defaultOwnedCharacters = new ArrayList<>();
    static {
        defaultOwnedCharacters.add(CharacterLoader.getDefaultCharacter());
    }

    private final List<GameCharacter> ownedCharacters = new ArrayList<>(defaultOwnedCharacters);

    private GameCharacter currentCharacter = CharacterLoader.getDefaultCharacter();

    private final GameState[] slots = new GameState[3];

    public User() {
        username = null;
        hashedPassword = null;
    }

    public User(String username, String password) {
        this.username = username;
        this.hashedPassword = new HashedPassword(password);
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

    public GameState getSlot(int i) {
        return slots[i];
    }

    public void setSlot(int i, GameState gameState) {
        slots[i] = gameState;
    }
}
