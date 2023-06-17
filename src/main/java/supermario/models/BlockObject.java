package supermario.models;

public class BlockObject {
    public final int x, y;
    public final BlockType type;
    public ItemType item;


    public BlockObject() {
        this.x = 0;
        this.y = 0;
        this.type = null;
        this.item = null;
    }
    public BlockObject(int x, int y, BlockType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public BlockObject(int x, int y, BlockType type, ItemType item) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.item = item;
    }
}
