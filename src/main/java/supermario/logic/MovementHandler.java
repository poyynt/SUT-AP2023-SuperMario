package supermario.logic;

import supermario.models.GameState;
import supermario.models.KeyboardState;
import supermario.models.State;
import supermario.models.Tile;
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
                if (MapHandler.sectionMap.width * 64 - playerX >= MainView.getInstance().getWidth() / 2)
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

        if (KeyboardState.pressedKeys.getOrDefault(KeyEvent.VK_UP, false) && getDoor() != null) {
            Tile door = getDoor();
            int toLevel = Integer.parseInt(door.properties.get("to_level"));
            int toSection = Integer.parseInt(door.properties.get("to_section"));
            GameHandler.loadSection(toLevel, toSection);
            return;
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
        int playerGridX = playerX / 64;
        int playerGridY = playerY / 64;
        if (playerY < 0)
            playerGridY = -1;
        if (playerX < 0)
            playerGridX = -1;
        if (playerGridX + 1 >= MapHandler.sectionMap.width)
            return false;
        Tile[] toCheck = new Tile[3];
        toCheck[1] = MapHandler.getTileAt(playerGridX + 1, playerGridY);
        toCheck[2] = MapHandler.getTileAt(playerGridX + 1, playerGridY + 1);
//        if (toCheck[2] != null)
//            if (playerGridY * 64 + 64 - playerY < 0 || playerGridY * 64 - playerY > 2)
//                toCheck[2] = null;
        if ((playerY + 128) % 64 <= 4)
            toCheck[2] = null;
        if ((playerY + 128) % 64 >= 60)
            toCheck[1] = null;
        boolean result = true;
        for (Tile t: toCheck) {
            if (t == null)
                continue;
            if (t.properties.getOrDefault("not_solid", "false").equals("false")) {
                result = false;
                TileCollisionHandler.handleCollisionWith(t, "left");
            }
        }
        return result;
    }

    private static boolean canMoveLeft() {
        int playerX = State.getCurrentGame().getPlayerX();
        int playerY = State.getCurrentGame().getPlayerY();
        int playerGridX = playerX / 64;
        int playerGridY = playerY / 64;
        if (playerY < 0)
            playerGridY = -1;
        if (playerX < 0)
            playerGridX = -1;
        if (playerX <= 4)
            return false;
        Tile[] toCheck = new Tile[3];
        int xAdditive = 0;
        if (playerX % 64 == 0)
            xAdditive = -1;
        toCheck[1] = MapHandler.getTileAt(playerGridX + xAdditive, playerGridY);
        toCheck[2] = MapHandler.getTileAt(playerGridX + xAdditive, playerGridY + 1);
//        if (toCheck[2] != null)
//            if (playerGridY * 64 + 64 - playerY < 0 || playerGridY * 64 - playerY > 2)
//                toCheck[2] = null;
        if ((playerY + 128) % 64 <= 4)
            toCheck[2] = null;
        if ((playerY + 128) % 64 >= 60)
            toCheck[2] = null;
        boolean result = true;
        for (Tile t: toCheck) {
            if (t == null)
                continue;
            if (t.properties.getOrDefault("not_solid", "false").equals("false")) {
                result = false;
                TileCollisionHandler.handleCollisionWith(t, "right");
            }
        }
        return result;
    }

    private static Tile getDoor() {
        int playerX = State.getCurrentGame().getPlayerX();
        int playerY = State.getCurrentGame().getPlayerY();
        int playerGridX = playerX / 64;
        int playerGridY = playerY / 64;
        if (playerY < 0)
            playerGridY = -1;
        if (playerX < 0)
            playerGridX = -1;
        if ((playerY + 128) % 64 > 8)
            return null;

        Tile[] toCheck = new Tile[3];

        toCheck[1] = MapHandler.getTileAt(playerGridX, playerGridY);
        toCheck[2] = MapHandler.getTileAt(playerGridX + 1, playerGridY);
        if (toCheck[2] != null)
            if (playerGridX * 64 + 64 - playerX < 0 || playerGridX * 64 - playerX > 2)
                toCheck[2] = null;
        if ((playerX + 128) % 64 < 4)
            toCheck[2] = null;
        for (Tile t: toCheck) {
            if (t == null)
                continue;
            if (t.properties.getOrDefault("is_door", "false").equals("true"))
                return t;
        }
        return null;
    }
}
