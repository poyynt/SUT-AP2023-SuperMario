package supermario.logic;

import supermario.models.GameState;
import supermario.models.Item;
import supermario.models.State;

public class ItemCollisionHandler {
    public static void handleCollisionWith(Item item, String direction) {
        if (item == null)
            return;

        item.properties.put("collision_happened", "true");
        item.properties.put(direction + "_collision_happened", "true");

        GameState gameState = State.getCurrentGame();
        String coinsPropertyName;
        if (item.properties.containsKey("collision_coins"))
            coinsPropertyName = "collision_coins";
        else
            coinsPropertyName = direction + "_collision_coins";
        gameState.addCoins(
                Integer.parseInt(
                        item.properties.getOrDefault(coinsPropertyName, "0")));
        item.properties.put("collision_coins", "0");

        String killsPropertyName;
        if (item.properties.containsKey("collision_kills"))
            killsPropertyName = "collision_kills";
        else
            killsPropertyName = direction + "_collision_kills";

        int livesToKill = Integer.parseInt(
                item.properties.getOrDefault(killsPropertyName, "0"));
        if (livesToKill > 0) {
            for (int i = 0; i < livesToKill - 1; i++)
                gameState.decreaseLives();
            GameHandler.die();
        }

        String powerupsPropertyName;
        if (item.properties.containsKey("collision_powerups_add"))
            powerupsPropertyName = "collision_powerups_add";
        else
            powerupsPropertyName = direction + "_collision_powerups_add";
        gameState.setPowerups(gameState.getPowerups() |
                Integer.parseInt(
                        item.properties.getOrDefault(powerupsPropertyName, "0")
                ));
        item.properties.put(powerupsPropertyName, "0");

        if (item.properties.containsKey("collision_set_powerups"))
            powerupsPropertyName = "collision_set_powerups";
        else
            powerupsPropertyName = direction + "_collision_set_powerups";
        if (item.properties.containsKey(powerupsPropertyName)) {
            gameState.setPowerups(
                    Integer.parseInt(
                            item.properties.get(powerupsPropertyName)
                    ));
            item.properties.remove(powerupsPropertyName);
        }
    }

    public static void handleItemCollisions() {
        int playerX = State.getCurrentGame().getPlayerX();
        int playerY = State.getCurrentGame().getPlayerY();
        int gridX = State.getCurrentGame().getPlayerX() / 64;
        int gridY = State.getCurrentGame().getPlayerY() / 64;
        if (State.getCurrentGame().getPlayerX() < 0)
            gridX = -1;
        if (State.getCurrentGame().getPlayerY() < 0)
            gridY = -1;
        Item[] toCheck;

        boolean collision_happened = false;
        if (playerX % 64 != 0) {
            // left
            toCheck = new Item[2];
            toCheck[0] = MapHandler.getItemAt(gridX + 1, gridY);
            toCheck[1] = MapHandler.getItemAt(gridX + 1, gridY + 1);
            if (playerY % 64 == 0)
                toCheck[1] = null;
            for (Item i: toCheck) {
                if (i == null)
                    continue;
                if (i.properties.getOrDefault("collision_happened", "false").equals("true"))
                    continue;
                if (i.properties.getOrDefault("hidden", "false").equals("true"))
                    continue;
                handleCollisionWith(i, "left");
                collision_happened = true;
            }
            if (collision_happened)
                return;
            // right
            toCheck = new Item[2];
            toCheck[0] = MapHandler.getItemAt(gridX, gridY);
            toCheck[1] = MapHandler.getItemAt(gridX, gridY + 1);
            if (playerY % 64 == 0)
                toCheck[1] = null;
            for (Item i: toCheck) {
                if (i == null)
                    continue;
                if (i.properties.getOrDefault("collision_happened", "false").equals("true"))
                    continue;
                if (i.properties.getOrDefault("hidden", "false").equals("true"))
                    continue;
                handleCollisionWith(i, "right");
                collision_happened = true;
            }
            if (collision_happened)
                return;
        }
        if (playerY % 64 != 0) {
            // up
            toCheck = new Item[2];
            toCheck[0] = MapHandler.getItemAt(gridX, gridY + 1);
            toCheck[1] = MapHandler.getItemAt(gridX + 1, gridY + 1);
            if (playerX % 64 == 0)
                toCheck[1] = null;
            for (Item i : toCheck) {
                if (i == null)
                    continue;
                if (i.properties.getOrDefault("collision_happened", "false").equals("true"))
                    continue;
                if (i.properties.getOrDefault("hidden", "false").equals("true"))
                    continue;
                handleCollisionWith(i, "up");
                collision_happened = true;
            }
            if (collision_happened)
                return;
        }
        // down
        toCheck = new Item[2];
        toCheck[0] = MapHandler.getItemAt(gridX, gridY);
        toCheck[1] = MapHandler.getItemAt(gridX + 1, gridY);
        if (playerX % 64 == 0)
            toCheck[1] = null;
        for (Item i: toCheck) {
            if (i == null)
                continue;
            if (i.properties.getOrDefault("collision_happened", "false").equals("true"))
                continue;
            if (i.properties.getOrDefault("hidden", "false").equals("true"))
                continue;
            handleCollisionWith(i, "down");
        }
    }
}
