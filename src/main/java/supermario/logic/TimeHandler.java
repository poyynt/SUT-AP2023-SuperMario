package supermario.logic;

import supermario.controllers.GameLoop;
import supermario.models.State;

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
