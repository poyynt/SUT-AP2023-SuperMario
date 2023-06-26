package supermario.models;

import supermario.logic.GravityItem;
import supermario.logic.ItemGravityHandler;

import java.awt.*;

public class ItemObject implements GravityItem {
    private int x, y;
    public final ItemType type;

    public ItemObject(int x, int y, ItemType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        new ItemGravityHandler(this).start();
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(x, y, 32, 32);
    }


}
