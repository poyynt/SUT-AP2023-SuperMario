package ir.sharif.math.ap2023.supermario.models;

import ir.sharif.math.ap2023.supermario.utils.HashedPassword;

public class User {
    private final String username;
    private final HashedPassword hashedPassword;
    private int coins = 0;
    private long highScore = 0;

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
}
