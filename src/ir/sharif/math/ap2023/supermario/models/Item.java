package ir.sharif.math.ap2023.supermario.models;

import java.util.HashMap;
import java.util.Map;

public class Item {
    public final int x, y;
    public final String name;
    public final Map<String, String> properties = new HashMap<>();

    public Item(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
