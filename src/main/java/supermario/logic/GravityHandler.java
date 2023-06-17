package supermario.logic;

import supermario.models.GameState;
import supermario.models.KeyboardState;
import supermario.models.State;
import supermario.models.BlockObject;
import supermario.views.MainView;

import java.awt.event.KeyEvent;

public class GravityHandler {
    private static boolean playerIsJumping = false;
    private static int playerJumpFrame = Integer.MAX_VALUE;
    public static void tick() {
        GameState state = State.getCurrentGame();
        if (state.getPlayerY() > MainView.getInstance().getHeight()) {
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
        int playerGridX = playerX / 32;
        int playerGridY = playerY / 32;
        if (playerY < 0)
            playerGridY = -1;
        if (playerX < 0)
            playerGridX = -1;
        BlockObject[] toCheck = new BlockObject[3];

        toCheck[1] = MapHandler.getTileAt(playerGridX, playerGridY + 1);
        toCheck[2] = MapHandler.getTileAt(playerGridX + 1, playerGridY + 1);
        if ((playerX + 128) % 32 <= 4)
            toCheck[2] = null;
        if ((playerX + 128) % 32 >= 28)
            toCheck[1] = null;
        boolean fall = true;
        for (BlockObject t: toCheck) {
            if (t == null)
                continue;
            fall = false;
            TileCollisionHandler.handleCollisionWith(t, "up");
//            }
        }
        return fall;
    }

    private static boolean playerCanGoUp() {
        int playerX = State.getCurrentGame().getPlayerX();
        int playerY = State.getCurrentGame().getPlayerY();
        int playerGridX = playerX / 32;
        int playerGridY = playerY / 32;
        if (playerY < 0)
            playerGridY = -1;
        if (playerX < 0)
            playerGridX = -1;
        BlockObject[] toCheck = new BlockObject[3];

        if ((playerY + 128) % 32 > 8)
            return true;
        int yAdditive = 0;
        if (playerY % 32 == 0)
            yAdditive = -1;
        toCheck[1] = MapHandler.getTileAt(playerGridX, playerGridY + yAdditive);
        toCheck[2] = MapHandler.getTileAt(playerGridX + 1, playerGridY + yAdditive);
        if ((playerX + 128) % 32 <= 4)
            toCheck[2] = null;
        if ((playerX + 128) % 32 >= 28)
            toCheck[1] = null;
        boolean result = true;
        for (BlockObject t: toCheck) {
            if (t == null)
                continue;
            result = false;
            TileCollisionHandler.handleCollisionWith(t, "down");
        }
        return result;
    }

    public static void reset() {
        playerIsJumping = false;
        playerJumpFrame = Integer.MAX_VALUE;
    }
}
