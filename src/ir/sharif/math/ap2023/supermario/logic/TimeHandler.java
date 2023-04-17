package ir.sharif.math.ap2023.supermario.logic;

import ir.sharif.math.ap2023.supermario.controllers.GameLoop;
import ir.sharif.math.ap2023.supermario.models.State;

public class TimeHandler {
    public static void tick() {
        if (State.getCurrentGame().getFramesElapsed() >= MapHandler.getTime() * GameLoop.getInstance().getFPS()) {
            GameHandler.die();
        }
    }

    public static int calculateTimeRemaining() {
        return MapHandler.getTime() - (State.getCurrentGame().getFramesElapsed() / (int) GameLoop.getInstance().getFPS());
    }
}
