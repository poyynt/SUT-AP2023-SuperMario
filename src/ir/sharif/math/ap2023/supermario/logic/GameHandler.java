package ir.sharif.math.ap2023.supermario.logic;

import ir.sharif.math.ap2023.supermario.models.GameState;
import ir.sharif.math.ap2023.supermario.models.State;
import ir.sharif.math.ap2023.supermario.models.User;

public class GameHandler {
    public static void makeNewGameInSlot(int slot) {
        GameState gameState = new GameState();
        State.getCurrentUser().getSlots()[slot] = gameState;
        State.setCurrentGame(gameState);
    }

    public static boolean slotIsContinuable(int slot) {
        if (State.getCurrentUser().getSlots()[slot] == null)
            return false;
        return State.getCurrentUser().getSlots()[slot].isStarted();
    }

    public static void clearNotStartedSlots() {
        for (User u: State.getAllUsers())
            for (int i = 0; i < 3; i++)
                if (u.getSlots()[i] != null && !u.getSlots()[i].isStarted())
                    u.getSlots()[i] = null;
    }

    public static void startNewGame() {
        State.getCurrentGame().setStarted();
    }
}
