package supermario.models;

import supermario.logic.GravityItem;

import java.awt.Rectangle;

public class Mario implements GravityItem {
    private int x = 0, y = 0;
    private int state = 0; // mini -> 0, mega -> 1, fire -> 2
    private int invincible = 0;

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

    public Rectangle getHitBox() {
        return new Rectangle(x, y, 32, 32);
    }
}
