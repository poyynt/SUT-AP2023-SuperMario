package supermario.models;

import supermario.controllers.AudioController;
import supermario.controllers.GameTimer;
import supermario.logic.GameHandler;
import supermario.logic.GravityItem;
import supermario.logic.MarioGravityHandler;

import java.awt.Rectangle;
import java.util.TimerTask;

public class Mario implements GravityItem {
    private int x = 0, y = 0;
    private int state = 0; // mini -> 0, mega -> 1, fire -> 2
    private int invincible = 0;
    private boolean sitting = false;
    private transient MarioGravityHandler gravityHandler;

    public Mario() {
    }

    public Mario(boolean notGson) {
        gravityHandler = new MarioGravityHandler(this);
        gravityHandler.start();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public int getInvincible() {
        return invincible;
    }

    public void setInvincible(int invincible) {
        this.invincible = invincible;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isSitting() {
        return sitting;
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }

    public Rectangle getHitBox() {
        if (sitting || state == 0)
            return new Rectangle(x, y, 32, 32);
        else
            return new Rectangle(x, y - 32, 32, 64);
    }

    public boolean isOnSomewhere() {
        return gravityHandler.isOnSomewhere();
    }

    public void getHit() {
        if (invincible <= 0) {
            AudioController.playWavAudioOnChannel("lifeLost", "LifeLost", 0);
            if (state > 0)
                state--;
            else
                GameHandler.die();
            invincible++;
            new GameTimer(new TimerTask() {
                @Override
                public void run() {
                    invincible--;
                }
            }, 1.5).start();
        }
    }

    public int getMoveSpeed() {
        return State.getCurrentGame().getCharacter().getMoveSpeed();
    }

}
