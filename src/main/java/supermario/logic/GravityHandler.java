package supermario.logic;

import supermario.models.*;
import supermario.views.MainView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

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
        Rectangle player = new Rectangle(playerX - 8, playerY, 48, 36);
        for (int dx = -2; dx <= 2; dx++) {
            if (playerGridY >= 20)
                return false;
            for (int dy = -2; dy <= 2; dy++) {
                BlockObject t = MapHandler.getBlockAt(playerGridX + dx, playerGridY + dy);
                if (t == null)
                    continue;
                Rectangle blockHitBox = t.getHitBox();
                if (blockHitBox.intersects(player)) {
                    int oc = player.outcode(blockHitBox.getCenterX(), blockHitBox.getCenterY());
                    if (oc == Rectangle2D.OUT_BOTTOM)
                        return false;
                }
            }
        }
        for (PipeObject p: MapHandler.sectionObject.pipes) {
            Rectangle pipeHitBox = p.getHitBox();
            if (pipeHitBox.intersects(player)) {
                int oc = player.outcode(pipeHitBox.getCenterX(), pipeHitBox.getCenterY());
                if (oc == Rectangle2D.OUT_BOTTOM)
                    return false;
            }
        }
        return true;
    }

    private static boolean playerCanGoUp() {
        int playerX = State.getCurrentGame().getPlayerX();
        int playerY = State.getCurrentGame().getPlayerY();
        int playerGridX = playerX / 32;
        int playerGridY = playerY / 32;
        Rectangle player = new Rectangle(playerX - 8, playerY, 48, 36);
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                BlockObject t = MapHandler.getBlockAt(playerGridX + dx, playerGridY + dy);
                if (t == null)
                    continue;
                Rectangle blockHitBox = t.getHitBox();
                if (blockHitBox.intersects(player)) {
                    int oc = player.outcode(blockHitBox.getCenterX(), blockHitBox.getCenterY());
                    if (oc == Rectangle2D.OUT_TOP)
                        return false;
                }
            }
        }
        return true;
    }

    public static void reset() {
        playerIsJumping = false;
        playerJumpFrame = Integer.MAX_VALUE;
    }
}
