package supermario.logic;

import supermario.controllers.AudioController;
import supermario.models.GameState;
import supermario.models.State;
import supermario.models.User;
import supermario.views.GameOverMenu;
import supermario.views.GameView;
import supermario.views.GameWonMenu;
import supermario.views.MainMenu;

public class GameHandler {
    public static void makeNewGameInSlot(int slot) {
        GameState gameState = new GameState();
        State.getCurrentUser().setSlot(slot, gameState);
        State.setCurrentGame(gameState);
    }

    public static void continueGameInSlot(int slot) {
        State.setCurrentGame(State.getCurrentUser().getSlot(slot));
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
        State.getCurrentGame().setStarted();
        State.getCurrentGame().setCharacter(State.getCurrentUser().getCurrentCharacter());
        GravityHandler.reset();
    }

    public static void loadSection(int level, int section) {
        GameState currentGame = State.getCurrentGame();
        currentGame.handleSectionEnd();
        if (level == -1 && section == -1) {
            State.getCurrentUser().setHighScore(
                    Math.max(
                            State.getCurrentUser().getHighScore(),
                            currentGame.calculateScore()));
            currentGame.setNotStarted();
            GameView.getInstance().remove();
            GameWonMenu.getInstance().show();
        }
        currentGame.setLevel(level);
        currentGame.setSection(section);
        currentGame.setPlayerX(0);
        currentGame.setPlayerY(0);
        currentGame.setScreenX(0);
        currentGame.setFramesElapsed(0);
        GravityHandler.reset();
    }

    public static void die() {
        GameState currentGame = State.getCurrentGame();
        currentGame.decreaseLives();
        AudioController.playWavAudioOnChannel("foreground", "Dead", 0);
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
            currentGame.setSecondsForScore(0);
            currentGame.setFramesElapsed(0);
            currentGame.setPowerups(0);
            GravityHandler.reset();
            MapHandler.forceReload();
        }
    }

    public static void pause() {
        GameView.getInstance().remove();
        MainMenu.getInstance().show();
    }
}
