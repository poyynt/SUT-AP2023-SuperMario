package supermario.models;

import supermario.logic.MapHandler;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

public class BlockObject {
    public final int x, y;
    public final BlockType type;
    public final ItemType item;
    private transient final Rectangle hitBox;
    private transient int state = 0;


    public BlockObject(int x, int y, BlockType type, ItemType item) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.hitBox = new Rectangle(x * 32, y * 32, 32, 32);
        if (item == null) {
            ItemType[] pool = {
                    ItemType.COIN, ItemType.COIN, ItemType.COIN, ItemType.COIN,
                    ItemType.FLOWER, ItemType.FLOWER, ItemType.FLOWER,
                    ItemType.MUSHROOM, ItemType.MUSHROOM,
                    ItemType.STAR
            };
            item = pool [ThreadLocalRandom.current().nextInt(pool.length)];
        }
        this.item = item;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void gotHit() {
        switch (type) {
            case SIMPLE -> {
                MapHandler.sectionObject.blocks.remove(this);
                State.getCurrentGame().addScore(1);
            }
            case COIN -> {
                ItemObject item = new ItemObject(this.x * 32, (this.y - 1) * 32, ItemType.COIN);
                MapHandler.sectionObject.items.add(item);
                BlockObject newBlock = new BlockObject(x, y, BlockType.SIMPLE, null);
                MapHandler.sectionObject.blocks.remove(this);
                MapHandler.sectionObject.blocks.add(newBlock);
                State.getCurrentGame().addScore(1);
            }
            case COINS -> {
                if (state < 5) {
                    State.getCurrentGame().addCoins(1);
                    state++;
                }
                else {
                    BlockObject newBlock = new BlockObject(x, y, BlockType.EMPTY, null);
                    MapHandler.sectionObject.blocks.remove(this);
                    MapHandler.sectionObject.blocks.add(newBlock);
                }
            }
            case QUESTION -> {
                ItemObject item = new ItemObject(this.x * 32, (this.y - 1) * 32, this.item);
                MapHandler.sectionObject.items.add(item);
                BlockObject newBlock = new BlockObject(x, y, BlockType.EMPTY, null);
                MapHandler.sectionObject.blocks.remove(this);
                MapHandler.sectionObject.blocks.add(newBlock);
                State.getCurrentGame().addScore(1);
            }
        }
    }
}
