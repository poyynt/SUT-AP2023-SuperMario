package ir.sharif.math.ap2023.supermario.logic;

import ir.sharif.math.ap2023.supermario.models.GameState;
import ir.sharif.math.ap2023.supermario.models.KeyboardState;
import ir.sharif.math.ap2023.supermario.models.State;
import ir.sharif.math.ap2023.supermario.models.Tile;

import java.awt.event.KeyEvent;

public class GravityHandler {
    private static boolean playerIsJumping = false;
    private static int playerJumpFrame = Integer.MAX_VALUE;
    public static void tick() {
        GameState state = State.getCurrentGame();
        if (state.getPlayerY() > 8 * 64) {
            GameHandler.die();
            return;
        }
        if (!playerIsJumping && playerShouldFall()) {
            state.setPlayerY(State.getCurrentGame().getPlayerY() + 8);
        }

        if (KeyboardState.pressedKeys.getOrDefault(KeyEvent.VK_SPACE, false)) {
            if (!playerIsJumping && !playerShouldFall()) {
                playerIsJumping = true;
                playerJumpFrame = State.getCurrentGame().getFramesElapsed();
            }
        }
        if (playerIsJumping) {
            if (State.getCurrentGame().getFramesElapsed() - playerJumpFrame < 50) {
                if (playerCanGoUp())
                    state.setPlayerY(State.getCurrentGame().getPlayerY() - State.getCurrentGame().getCharacter().getJumpSpeed());
                else {
                    playerJumpFrame = Integer.MAX_VALUE;
                    playerIsJumping = false;
                }
            }
            else if (State.getCurrentGame().getFramesElapsed() - playerJumpFrame >= 50){
                playerJumpFrame = Integer.MAX_VALUE;
                playerIsJumping = false;
            }
        }
    }

    private static boolean playerShouldFall() {
        int playerX = State.getCurrentGame().getPlayerX();
        int playerY = State.getCurrentGame().getPlayerY();
        int playerGridX = playerX / 64;
        int playerGridY = playerY / 64;
        if (playerY < 0)
            playerGridY = -1;
        if (playerX < 0)
            playerGridX = -1;
        Tile[] toCheck = new Tile[3];

        toCheck[1] = MapHandler.getTileAt(playerGridX, playerGridY + 1);
        toCheck[2] = MapHandler.getTileAt(playerGridX + 1, playerGridY + 1);
//        if (toCheck[2] != null)
//            if (playerGridX * 64 + 64 - playerX < 0 || playerGridX * 64 - playerX > 2)
//                toCheck[2] = null;
        if ((playerX + 128) % 64 <= 8)
            toCheck[2] = null;
        if ((playerX + 128) % 64 >= 56)
            toCheck[1] = null;
        boolean fall = true;
        for (Tile t: toCheck) {
            if (t == null)
                continue;
            if (t.properties.getOrDefault("not_solid", "false").equals("false")) {
                fall = false;
                TileCollisionHandler.handleCollisionWith(t, "up");
            }
        }
        return fall;
    }

    private static boolean playerCanGoUp() {
        int playerX = State.getCurrentGame().getPlayerX();
        int playerY = State.getCurrentGame().getPlayerY();
        int playerGridX = playerX / 64;
        int playerGridY = playerY / 64;
        if (playerY < 0)
            playerGridY = -1;
        if (playerX < 0)
            playerGridX = -1;
        Tile[] toCheck = new Tile[3];

        if ((playerY + 128) % 64 > 8)
            return true;
        int yAdditive = 0;
        if (playerY % 64 == 0)
            yAdditive = -1;
        toCheck[1] = MapHandler.getTileAt(playerGridX, playerGridY + yAdditive);
        toCheck[2] = MapHandler.getTileAt(playerGridX + 1, playerGridY + yAdditive);
//        if (toCheck[2] != null)
//            if (playerGridX * 64 + 64 - playerX < 0 || playerGridX * 64 - playerX > 2)
//                toCheck[2] = null;
        if ((playerX + 128) % 64 <= 8)
            toCheck[2] = null;
        if ((playerX + 128) % 64 >= 56)
            toCheck[1] = null;
        boolean result = true;
        for (Tile t: toCheck) {
            if (t == null)
                continue;
            if (t.properties.getOrDefault("not_solid", "false").equals("false")) {
                result = false;
                TileCollisionHandler.handleCollisionWith(t, "down");
            }
        }
        return result;
    }

    public static void reset() {
        playerIsJumping = false;
        playerJumpFrame = Integer.MAX_VALUE;
    }
}
