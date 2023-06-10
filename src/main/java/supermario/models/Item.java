package supermario.models;

import supermario.controllers.GameLoop;
import supermario.logic.SpriteLoader;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Item {
    public final int x, y;
    public final String name;
    public final Map<String, String> properties = new HashMap<>();
    private transient Image image;

    public Item() {
        this.x = 0;
        this.y = 0;
        this.name = null;
    }
    public Item(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public void tick() {
        if (properties.containsKey("show_hide_interval")) {
            double showHideInterval = Double.parseDouble(properties.get("show_hide_interval"));
            int lastShowHideModified = Integer.parseInt(properties.getOrDefault("last_show_hide_modified", "0"));
            double framesPassed = State.getCurrentGame().getFramesElapsed() - lastShowHideModified;
            framesPassed /= GameLoop.getInstance().getFPS();
            if (framesPassed >= showHideInterval) {
                if (properties.getOrDefault("hidden", "false").equals("false"))
                    properties.put("hidden", "true");
                else
                    properties.put("hidden", "false");
                properties.put("last_show_hide_modified", String.valueOf(State.getCurrentGame().getFramesElapsed()));
            }
        }
        for (String key: new String[]{"", "up_", "down_", "left_", "right_"}) {
            if (properties.getOrDefault("hide_on_" + key + "collision", "false").equals("true"))
                if (properties.getOrDefault(key + "collision_happened", "false").equals("true"))
                    properties.put("hidden", "true");
        }
    }

    public Image getImage() {
        if (image == null)
            image = SpriteLoader.loadSpriteForItem(this);
        return image;
    }
}
