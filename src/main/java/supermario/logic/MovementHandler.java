package supermario.logic;

import supermario.models.GameState;
import supermario.models.KeyboardState;
import supermario.models.State;
import supermario.models.BlockObject;
import supermario.views.MainView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class MovementHandler {

    private static boolean playerFacingRight = true;

    public static void tick() {
        GameState gameState = State.getCurrentGame();
        int playerX = gameState.getPlayer().getX();
        int playerY = gameState.getPlayer().getY();
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

        if (gameState.getPlayer().getState() > 0) {
            if (KeyboardState.pressedKeys.getOrDefault(KeyEvent.VK_SHIFT, false)
                    && !KeyboardState.pressedKeys.getOrDefault(KeyEvent.VK_SPACE, false))
                gameState.getPlayer().setSitting(true);
            else
                gameState.getPlayer().setSitting(false);
        }

        gameState.getPlayer().setX(playerX);
        gameState.getPlayer().setY(playerY);
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
        Rectangle player = new Rectangle(playerX - 8, playerY, 48, 32);
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                BlockObject t = MapHandler.getBlockAt(playerGridX + dx, playerGridY + dy);
                if (t == null)
                    continue;
                Rectangle blockHitBox = t.getHitBox();
                if (blockHitBox.intersects(player)) {
                    int oc = player.outcode(blockHitBox.getCenterX(), blockHitBox.getCenterY());
                    if (oc == Rectangle2D.OUT_RIGHT)
                        return false;
                }
            }
        }
        return true;
    }

    private static boolean canMoveLeft() {
        int playerX = State.getCurrentGame().getPlayerX();
        int playerY = State.getCurrentGame().getPlayerY();
        if (playerX <= 0)
            return false;
        int playerGridX = playerX / 32;
        int playerGridY = playerY / 32;
        Rectangle player = new Rectangle(playerX - 8, playerY, 48, 32);
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                BlockObject t = MapHandler.getBlockAt(playerGridX + dx, playerGridY + dy);
                if (t == null)
                    continue;
                Rectangle blockHitBox = t.getHitBox();
                if (blockHitBox.intersects(player)) {
                    int oc = player.outcode(blockHitBox.getCenterX(), blockHitBox.getCenterY());
                    if (oc == Rectangle2D.OUT_LEFT)
                        return false;
                }
            }
        }
        return true;
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

        toCheck[1] = MapHandler.getBlockAt(playerGridX, playerGridY);
        toCheck[2] = MapHandler.getBlockAt(playerGridX + 1, playerGridY);
        if (toCheck[2] != null)
            if (playerGridX * 32 + 32 - playerX < 0 || playerGridX * 32 - playerX > 2)
                toCheck[2] = null;
        if ((playerX + 128) % 32 < 2)
            toCheck[2] = null;
        for (BlockObject t : toCheck) {
            if (t == null)
                continue;
            return t;
        }
        return null;
    }
}
