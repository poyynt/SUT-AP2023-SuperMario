package supermario.logic;

import supermario.controllers.Loop;
import supermario.models.BlockObject;
import supermario.models.KeyboardState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MarioGravityHandler extends Loop {
    private static List<MarioGravityHandler> instances = new CopyOnWriteArrayList<>();
    private GravityItem target;
    private double vy = 0;
    private double g = 0.08;

    public MarioGravityHandler(GravityItem target) {
        super(60.0);
        for (MarioGravityHandler instance: instances)
            if (instance.target == target)
                instance.stop();
        instances.removeIf(i -> i.target == target);
        this.target = target;
        instances.add(this);
    }

    @Override
    public void update() {
        int y = target.getY();
        Rectangle hitBox = target.getHitBox();
        hitBox.translate(0, (int) vy);
        boolean collides = false, canJump = false;
        if (MapHandler.sectionObject != null) {
            for (BlockObject b : MapHandler.sectionObject.blocks) {
                Rectangle blockHitBox = b.getHitBox();
                if (blockHitBox.intersects(hitBox)) {
                    int oc = hitBox.outcode(blockHitBox.getCenterX(), blockHitBox.getCenterY());
                    if (
                            (vy >= 0 && oc == Rectangle2D.OUT_BOTTOM) ||
                                    (vy < 0 && oc == Rectangle2D.OUT_TOP))
                        collides = true;
                    if (vy < 0 && oc == Rectangle2D.OUT_TOP) {
                        b.gotHit();
                        vy = 0.1;
                    }
                    if (vy >= 0 && oc == Rectangle2D.OUT_BOTTOM)
                        canJump = true;
                }
            }
        }
        if (y + vy > 20 * 32)
            collides = canJump = true;
        if (collides)
            vy = 0.;
        else {
            target.setY((int) (y + vy));
            vy += g;
        }
        if (collides && canJump) {
            if (KeyboardState.pressedKeys.getOrDefault(KeyEvent.VK_SPACE, false)) {
                vy = -5.;
            }
        }
    }
}
