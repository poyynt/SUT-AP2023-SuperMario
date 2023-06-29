package supermario.models;

import supermario.controllers.AudioController;
import supermario.logic.TimeHandler;

public class GameState { // section
    private int level = 1;
    private int section = 1;
    private int lastLevel = 1;
    private int lastSection = 1;
    private int lives = 3;
    private Mario player = new Mario(true);
    private int screenX = 0;
    private int coins = 0;
    private int currentScore = 0;
    private int secondsForScore = 0;
    private int totalCoins = 0;
    private int killedBosses = 0;
    private int powerups = 0; // bitmask?
    private int framesElapsed = 0;
    private String difficulty = "";
    private GameCharacter character;

    public static final String[] allDifficulties = {
            "Easy",
            "Medium",
            "Hard"
    };

    private boolean started = false;
    private boolean running = false;

    public int getLives() {
        return lives;
    }

    public void decreaseLives() {
        this.lives--;
    }

    public int getPlayerX() {
        return player.getX();
    }

    public void setPlayerX(int playerX) {
        player.setX(playerX);
    }

    public int getPlayerY() {
        return player.getY();
    }

    public void setPlayerY(int playerY) {
        player.setY(playerY);
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

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int amount) {
        if (isNewSection())
            this.coins += amount;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public void addScore(int amount) {
        this.currentScore += amount;
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

    public void setFramesElapsed(int framesElapsed) {
        this.framesElapsed = framesElapsed;
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

    public void setNotStarted() {
        this.started = false;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public GameCharacter getCharacter() {
        return character;
    }

    public void setCharacter(GameCharacter character) {
        this.character = character;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.lastLevel = Math.max(lastLevel, level);
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
        this.lastSection = Math.max(lastSection, section);
    }

    public void setSecondsForScore(int secondsForScore) {
        this.secondsForScore = secondsForScore;
    }

    public Mario getPlayer() {
        return player;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void handleSectionEnd() {
        AudioController.playWavAudioOnChannel("foreground", "CourseClear", 0);
        totalCoins += coins;
        State.getCurrentUser().addCoins(coins);
        coins = 0;
        powerups = 0;
        secondsForScore += TimeHandler.calculateTimeRemaining();
    }

    public boolean isNewSection() {
        return level == lastLevel && section == lastSection;
    }

    public int calculateMomentaryScore() {
        int sum = currentScore;
        sum += coins * 10;
        sum += killedBosses * 15;
        return sum;
    }

    public int calculateScore() {
        int sum = currentScore;
        sum += totalCoins * 10;
        sum += lives * 20;
        sum += killedBosses * 15;
        sum += secondsForScore;
        sum *= Integer.bitCount(powerups) + 1;
        return sum;
    }
}
