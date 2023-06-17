package supermario.models;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

public class BlockObject {
    public final int x, y;
    public final BlockType type;
    public final ItemType item;


    public BlockObject(int x, int y, BlockType type, ItemType item) {
        this.x = x;
        this.y = y;
        this.type = type;
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
}
