package supermario.models;

import supermario.logic.BulletMovementHandler;
import supermario.logic.MapHandler;

import java.awt.*;

public class BulletObject {
    private int x, y, ix;
    private BulletMovementHandler movementHandler;

    public BulletObject(int x, int y) {
        this.ix = x;
        this.x = x;
        this.y = y;
        this.movementHandler = new BulletMovementHandler(this);
        this.movementHandler.start();
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

    public int getIx() {
        return ix;
    }

    public Rectangle getHitBox() {
        return new Rectangle(x - 1, y - 1, 2, 2);
    }

    public void destroy() {
        MapHandler.sectionObject.bullets.remove(this);
        this.movementHandler.stop();
    }
}
