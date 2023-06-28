package supermario.controllers;

import supermario.models.State;

import java.util.TimerTask;

public class GameTimer extends Loop {
    private int framesElapsed = 0;
    private double target = 0.;
    private TimerTask task;

    public GameTimer(TimerTask task, double secs) {
        super(30.);
        this.target = secs;
        this.task = task;
    }

    @Override
    public void update() {
        if (State.getCurrentGame() == null || !State.getCurrentGame().isRunning())
            return;
        framesElapsed++;
        if (framesElapsed / getFPS() >= target) {
            this.task.run();
            this.stop();
        }
    }
}
