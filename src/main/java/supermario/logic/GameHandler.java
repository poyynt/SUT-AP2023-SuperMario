package supermario.logic;

import supermario.controllers.AudioController;
import supermario.models.*;
import supermario.views.GameOverMenu;
import supermario.views.GameView;
import supermario.views.GameWonMenu;
import supermario.views.MainMenu;

public class GameHandler {
    public static void makeNewGameInSlot(int slot) {
        GameState gameState = new GameState();
        State.getCurrentUser().setSlot(slot, gameState);
        State.setCurrentGame(gameState);
        State.getCurrentGame().setRunning(true);
    }

    public static void continueGameInSlot(int slot) {
        State.setCurrentGame(State.getCurrentUser().getSlot(slot));
        State.getCurrentGame().setRunning(true);
    }

    public static boolean slotIsContinuable(int slot) {
        if (State.getCurrentUser().getSlot(slot) == null)
            return false;
        return State.getCurrentUser().getSlot(slot).isStarted();
    }

    public static void clearNotStartedSlots() {
        for (User u : State.getAllUsers())
            for (int i = 0; i < 3; i++)
                if (u.getSlot(i) != null && !u.getSlot(i).isStarted())
                    u.setSlot(i, null);
    }

    public static void startNewGame() {
        MapHandler.loadMap();
        State.getCurrentGame().getPlayer().setState(MapHandler.fileObject.marioState);
        State.getCurrentGame().setLives(MapHandler.fileObject.hearts);
        State.getCurrentGame().setStarted();
        State.getCurrentGame().setCharacter(State.getCurrentUser().getCurrentCharacter());
        State.getCurrentGame().setRunning(true);
    }

    public static void loadSection(int level, int section) {
        GameState currentGame = State.getCurrentGame();
        if (level == -1 && section == -1) {
            State.getCurrentUser().setHighScore(
                    Math.max(
                            State.getCurrentUser().getHighScore(),
                            currentGame.calculateScore()));
            currentGame.setNotStarted();
            GameView.getInstance().remove();
            GameWonMenu.getInstance().show();
        }
        currentGame.getPlayer().setX(0);
        currentGame.getPlayer().setY(0);
        currentGame.setScreenX(0);
        currentGame.setFramesElapsed(0);
        currentGame.setLevel(level);
        currentGame.setSection(section);
        currentGame.handleSectionEnd();
        State.getCurrentGame().setRunning(true);
        for (EnemyObject e : MapHandler.sectionObject.enemies)
            e.stopHandlers();
        for (ItemObject i : MapHandler.sectionObject.items)
            i.stopHandlers();
    }

    public static void die() {
        GameState currentGame = State.getCurrentGame();
        currentGame.decreaseLives();
        State.getCurrentGame().addScore(-20);
        int Dn = currentGame.getCoins();
        Dn += State.getCurrentGame().getPlayer().getX() / (MapHandler.sectionObject.length * 32);
        Dn /= 4;
        State.getCurrentGame().addCoins(-Dn);
        AudioController.playWavAudioOnChannel("foreground", "Dead", 0);
        for (EnemyObject e : MapHandler.sectionObject.enemies)
            e.stopHandlers();
        for (ItemObject i : MapHandler.sectionObject.items)
            i.stopHandlers();
        if (currentGame.getLives() == 0) {
            State.getCurrentUser().setHighScore(
                    Math.max(
                            State.getCurrentUser().getHighScore(),
                            currentGame.calculateScore()));
            currentGame.setNotStarted();
            GameView.getInstance().remove();
            GameOverMenu.getInstance().show();
        } else {
            currentGame.setSection(1);
            currentGame.setPlayerX(0);
            currentGame.setPlayerY(0);
            currentGame.setScreenX(0);
            currentGame.setCoins(0);
            currentGame.setCurrentScore(0);
            currentGame.setSecondsForScore(0);
            currentGame.setFramesElapsed(0);
            currentGame.setPowerups(0);
            MapHandler.forceReload();
            State.getCurrentGame().setRunning(true);
        }
    }

    public static void pause() {
        State.getCurrentGame().setRunning(false);
        GameView.getInstance().remove();
        MainMenu.getInstance().show();
    }
}
