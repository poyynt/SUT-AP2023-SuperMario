package supermario.models;

import java.util.HashMap;
import java.util.Map;

public class Tile {
    public final int x, y;
    public final String name;
    public final Map<String, String> properties = new HashMap<>();


    public Tile() {
        this.x = 0;
        this.y = 0;
        this.name = null;
    }
    public Tile(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
