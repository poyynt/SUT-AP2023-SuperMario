package supermario.logic;

import supermario.models.GameState;
import supermario.models.State;
import supermario.models.BlockObject;

public class TileCollisionHandler {
    public static void handleCollisionWith(BlockObject t, String direction) {
        if (t == null)
            return;

        GameState gameState = State.getCurrentGame();
//        gameState.addCoins(
//                Integer.parseInt(
//                        t.properties.getOrDefault(direction + "_" + "collision_coins", "0")));
//        t.properties.put(direction + "_" + "collision_coins", "0");

//        int livesToKill = Integer.parseInt(
//                t.properties.getOrDefault(direction + "_collision_kills", "0"));
//        if (livesToKill > 0) {
//            for (int i = 0; i < livesToKill - 1; i++)
//                gameState.decreaseLives();
//            GameHandler.die();
//        }

//        gameState.setPowerups(gameState.getPowerups() |
//                Integer.parseInt(
//                        t.properties.getOrDefault(direction + "_collision_powerups_add", "0")
//                ));
//        t.properties.put(direction + "_collision_powerups_add", "0");
//
//        if (t.properties.containsKey(direction + "_collision_set_powerups")) {
//            gameState.setPowerups(
//                    Integer.parseInt(
//                            t.properties.get(direction + "_collision_set_powerups")
//                    ));
//            t.properties.remove(direction + "_collision_set_powerups");
//        }
    }
}
