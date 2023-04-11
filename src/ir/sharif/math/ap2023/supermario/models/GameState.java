package ir.sharif.math.ap2023.supermario.models;

public class GameState {
    private int lives = 3;
    private int playerX = 0;
    private int playerY = 0;
    private int screenX = 0;
    private int coins = 0;
    private int killedBosses = 0;
    private int powerups = 0; // bitmask?
    private int framesElapsed = 0;

    private boolean started = false;

    public int getLives() {
        return lives;
    }

    public void decreaseLives() {
        this.lives--;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public int getKilledBosses() {
        return killedBosses;
    }

    public void incrementKilledBosses() {
        this.killedBosses++;
    }

    public int getPowerups() {
        return powerups;
    }

    public void setPowerups(int powerups) {
        this.powerups = powerups;
    }

    public int getFramesElapsed() {
        return framesElapsed;
    }

    public void incrementFramesElapsed() {
        this.framesElapsed++;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted() {
        this.started = true;
    }
}
