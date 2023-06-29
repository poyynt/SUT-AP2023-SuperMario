package supermario.logic;

import supermario.models.*;
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

        if (KeyboardState.pressedKeys.getOrDefault(KeyEvent.VK_Q, false)) {
            if (gameState.getPlayer().getState() == 2) {
                if (gameState.getPlayer().isOnSomewhere()) {
                    int bulletY = gameState.getPlayer().getY() - 16;
                    if (gameState.getPlayer().isSitting())
                        bulletY += 32;
                    BulletObject bulletObject = new BulletObject(gameState.getPlayer().getX(), bulletY);
                    MapHandler.sectionObject.bullets.add(bulletObject);
                }
            }
        }

        if (KeyboardState.pressedKeys.getOrDefault(KeyEvent.VK_N, false)) {
            if (State.getCurrentGame().getPlayer().getX() + 32 >= MapHandler.sectionObject.length * 32) {
                int section = State.getCurrentGame().getSection();
                int level = State.getCurrentGame().getLevel();
                if (section < MapHandler.lastSectionForLevel(level)) {
                    GameHandler.loadSection(level, section + 1);
                } else if (level < MapHandler.lastLevel()) {
                    GameHandler.loadSection(level + 1, 1);
                } else GameHandler.loadSection(-1, -1);
            }
        }

        gameState.getPlayer().setX(playerX);
        gameState.getPlayer().setY(playerY);
        gameState.setScreenX(screenX);
    }

    public static boolean isPlayerFacingRight() {
        return playerFacingRight;
    }

    private static boolean canMoveRight() {
        int playerX = State.getCurrentGame().getPlayer().getX();
        int playerY = State.getCurrentGame().getPlayer().getY();
        int playerGridX = playerX / 32;
        int playerGridY = playerY / 32;
        Rectangle player = State.getCurrentGame().getPlayer().getHitBox();
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
        int playerX = State.getCurrentGame().getPlayer().getX();
        int playerY = State.getCurrentGame().getPlayer().getY();
        if (playerX <= 0)
            return false;
        int playerGridX = playerX / 32;
        int playerGridY = playerY / 32;
        Rectangle player = State.getCurrentGame().getPlayer().getHitBox();
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
}
