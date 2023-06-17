package supermario.models;

import java.util.HashMap;
import java.util.Map;

public class BlockObject {
    public final int x, y;
    public final String name;
    public final Map<String, String> properties = new HashMap<>();


    public BlockObject() {
        this.x = 0;
        this.y = 0;
        this.name = null;
    }
    public BlockObject(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
