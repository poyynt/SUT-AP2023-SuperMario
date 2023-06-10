package supermario.models;

import java.util.HashMap;
import java.util.Map;

public class Tile {
    public final int x, y;
    public final String name;
    public final Map<String, String> properties = new HashMap<>();

    public Tile(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
