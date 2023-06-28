package supermario.models;

import supermario.logic.GravityItem;
import supermario.logic.ItemCollisionHandler;
import supermario.logic.ItemGravityHandler;
import supermario.logic.MapHandler;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class ItemObject implements GravityItem {
    private int x, y;
    public final ItemType type;
    private final ItemGravityHandler gravityHandler;
    private final ItemCollisionHandler collisionHandler;

    public ItemObject(int x, int y, ItemType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.gravityHandler = new ItemGravityHandler(this);
        this.gravityHandler.start();
        this.collisionHandler = new ItemCollisionHandler(this);
        this.collisionHandler.start();
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

    public synchronized void getConsumed() {
        switch (type) {
            case COIN -> State.getCurrentGame().addCoins(1);
            case FLOWER -> {
                State.getCurrentGame().addScore(20);
                int marioState = State.getCurrentGame().getPlayer().getState();
                marioState = Math.max(marioState + 1, 2);
                State.getCurrentGame().getPlayer().setState(marioState);
            }
            case MUSHROOM -> {
                State.getCurrentGame().addScore(30);
                int marioState = State.getCurrentGame().getPlayer().getState();
                marioState = Math.max(marioState + 1, 2);
                State.getCurrentGame().getPlayer().setState(marioState);
            }
            case STAR -> {
                State.getCurrentGame().addScore(40);
                int marioState = State.getCurrentGame().getPlayer().getState();
                marioState = Math.max(marioState + 1, 2);
                State.getCurrentGame().getPlayer().setState(marioState);
                Mario mario = State.getCurrentGame().getPlayer();
                mario.setInvincible(mario.getInvincible() + 1);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public synchronized void run() {
                        mario.setInvincible(mario.getInvincible() - 1);
                    }
                }, 15000);
            }
        }
        MapHandler.sectionObject.items.remove(this);
        this.gravityHandler.stop();
        this.collisionHandler.stop();
    }
}
