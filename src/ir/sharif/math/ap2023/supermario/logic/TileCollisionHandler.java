package ir.sharif.math.ap2023.supermario.logic;

import ir.sharif.math.ap2023.supermario.models.GameState;
import ir.sharif.math.ap2023.supermario.models.State;
import ir.sharif.math.ap2023.supermario.models.Tile;

public class TileCollisionHandler {
    public static void handleCollisionWith(Tile t, String direction) {
        if (t == null)
            return;

        GameState gameState = State.getCurrentGame();
        gameState.addCoins(
                Integer.parseInt(
                        t.properties.getOrDefault(direction + "_" + "collision_coins", "0")));
        t.properties.put(direction + "_" + "collision_coins", "0");

        int livesToKill = Integer.parseInt(
                t.properties.getOrDefault(direction + "_collision_kills", "0"));
        for (int i = 0; i < livesToKill; i++)
            gameState.decreaseLives();

        gameState.setPowerups(gameState.getPowerups() |
                Integer.parseInt(
                        t.properties.getOrDefault(direction + "_collision_powerups_add", "0")
                ));
        t.properties.put(direction + "_collision_powerups_add", "0");

        if (t.properties.containsKey(direction + "_collision_setpowerups")) {
            gameState.setPowerups(
                    Integer.parseInt(
                            t.properties.get(direction + "_collision_setpowerups")
                    ));
            t.properties.remove(direction + "_collision_setpowerups");
        }
    }
}
