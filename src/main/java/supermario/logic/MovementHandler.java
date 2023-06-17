package supermario.logic;

import supermario.models.GameState;
import supermario.models.KeyboardState;
import supermario.models.State;
import supermario.models.BlockObject;
import supermario.views.MainView;

import java.awt.event.KeyEvent;

public class MovementHandler {

    private static boolean playerFacingRight = true;
    public static void tick() {
        GameState gameState = State.getCurrentGame();
        int playerX = gameState.getPlayerX();
        int playerY = gameState.getPlayerY();
        int screenX = gameState.getScreenX();

        if (KeyboardState.pressedKeys.getOrDefault(KeyEvent.VK_RIGHT, false) && canMoveRight()) {
            playerX += gameState.getCharacter().getMoveSpeed();
            if (playerX - screenX >= MainView.getInstance().getWidth() / 2)
                if (MapHandler.sectionObject.length * 32 - playerX >= MainView.getInstance().getWidth() / 2)
                    screenX += gameState.getCharacter().getMoveSpeed();
            playerFacingRight = true;

        }
        if (KeyboardState.pressedKeys.getOrDefault(KeyEvent.VK_LEFT, false) && canMoveLeft()) {
            playerX -= gameState.getCharacter().getMoveSpeed();
            if (playerX - screenX <= MainView.getInstance().getWidth() / 4)
                if (screenX - gameState.getCharacter().getMoveSpeed() >= 0)
                    screenX -= gameState.getCharacter().getMoveSpeed();
            playerFacingRight = false;
        }

        gameState.setPlayerX(playerX);
        gameState.setPlayerY(playerY);
        gameState.setScreenX(screenX);
    }

    public static boolean isPlayerFacingRight() {
        return playerFacingRight;
    }

    private static boolean canMoveRight() {
        int playerX = State.getCurrentGame().getPlayerX();
        int playerY = State.getCurrentGame().getPlayerY();
        int playerGridX = playerX / 32;
        int playerGridY = playerY / 32;
        if (playerY < 0)
            playerGridY = -1;
        if (playerX < 0)
            playerGridX = -1;
        if (playerGridX + 1 >= MapHandler.sectionObject.length)
            return false;
        BlockObject[] toCheck = new BlockObject[3];
        toCheck[1] = MapHandler.getTileAt(playerGridX + 1, playerGridY);
        toCheck[2] = MapHandler.getTileAt(playerGridX + 1, playerGridY + 1);
        if ((playerY + 128) % 32 <= 2)
            toCheck[2] = null;
        if ((playerY + 128) % 32 >= 30)
            toCheck[1] = null;
        boolean result = true;
        for (BlockObject t: toCheck) {
            if (t == null)
                continue;
            result = false;
            TileCollisionHandler.handleCollisionWith(t, "left");
        }
        return result;
    }

    private static boolean canMoveLeft() {
        int playerX = State.getCurrentGame().getPlayerX();
        int playerY = State.getCurrentGame().getPlayerY();
        int playerGridX = playerX / 32;
        int playerGridY = playerY / 32;
        if (playerY < 0)
            playerGridY = -1;
        if (playerX < 0)
            playerGridX = -1;
        if (playerX <= 4)
            return false;
        BlockObject[] toCheck = new BlockObject[3];
        int xAdditive = 0;
        if (playerX % 32 == 0)
            xAdditive = -1;
        toCheck[1] = MapHandler.getTileAt(playerGridX + xAdditive, playerGridY);
        toCheck[2] = MapHandler.getTileAt(playerGridX + xAdditive, playerGridY + 1);
        if ((playerY + 128) % 32 <= 2)
            toCheck[2] = null;
        if ((playerY + 128) % 32 >= 30)
            toCheck[2] = null;
        boolean result = true;
        for (BlockObject t: toCheck) {
            if (t == null)
                continue;
            result = false;
            TileCollisionHandler.handleCollisionWith(t, "right");
        }
        return result;
    }

    private static BlockObject getDoor() {
        int playerX = State.getCurrentGame().getPlayerX();
        int playerY = State.getCurrentGame().getPlayerY();
        int playerGridX = playerX / 32;
        int playerGridY = playerY / 32;
        if (playerY < 0)
            playerGridY = -1;
        if (playerX < 0)
            playerGridX = -1;
        if ((playerY + 128) % 32 > 8)
            return null;

        BlockObject[] toCheck = new BlockObject[3];

        toCheck[1] = MapHandler.getTileAt(playerGridX, playerGridY);
        toCheck[2] = MapHandler.getTileAt(playerGridX + 1, playerGridY);
        if (toCheck[2] != null)
            if (playerGridX * 32 + 32 - playerX < 0 || playerGridX * 32 - playerX > 2)
                toCheck[2] = null;
        if ((playerX + 128) % 32 < 2)
            toCheck[2] = null;
        for (BlockObject t: toCheck) {
            if (t == null)
                continue;
            return t;
        }
        return null;
    }
}
