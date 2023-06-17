package supermario.models;

import java.util.HashMap;
import java.util.Map;

public class BlockObject {
    public final int x, y;
    public final BlockType type;
    public final Map<String, String> properties = new HashMap<>();


    public BlockObject() {
        this.x = 0;
        this.y = 0;
        this.type = null;
    }
    public BlockObject(int x, int y, BlockType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
