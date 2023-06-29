package supermario.logic;

import supermario.controllers.Loop;
import supermario.models.BlockObject;
import supermario.models.EnemyObject;
import supermario.models.EnemyType;
import supermario.models.State;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GoombaMovementHandler extends EnemyMovementHandler {
    private double vx = 1;

    public GoombaMovementHandler(EnemyObject target) {
        super(target);
    }

    @Override
    public void update() {
        if (State.getCurrentGame() == null || !State.getCurrentGame().isRunning())
            return;
        if (target.type == EnemyType.KOOPA && target.getPhase() == 2)
            return;
        int x = target.getX();
        Rectangle hitBox = target.getHitBox();
        hitBox.translate((int) vx, 0);
        boolean collides = false;
        if (MapHandler.sectionObject != null) {
            for (BlockObject b : MapHandler.sectionObject.blocks) {
                Rectangle blockHitBox = b.getHitBox();
                if (blockHitBox.intersects(hitBox)) {
                    int oc = hitBox.outcode(blockHitBox.getCenterX(), blockHitBox.getCenterY());
                    if (
                            (vx >= 0 && oc == Rectangle2D.OUT_RIGHT) ||
                                    (vx <= 0 && oc == Rectangle2D.OUT_LEFT))
                        collides = true;
                }
            }
        }
        hitBox = target.getHitBox();
        if (vx >= 0)
            hitBox.translate(8, 1);
        else
            hitBox.translate(-8, 1);
        boolean falls = true;
        if (MapHandler.sectionObject != null) {
            for (BlockObject b : MapHandler.sectionObject.blocks) {
                Rectangle blockHitBox = b.getHitBox();
                if (blockHitBox.intersects(hitBox)) {
                    int oc = hitBox.outcode(blockHitBox.getCenterX(), blockHitBox.getCenterY());
                    if (oc == Rectangle2D.OUT_BOTTOM)
                        falls = false;
                }
            }
        }
        if (target.getY() >= 20 * 32 - 8)
            falls = false;
        if (collides || falls)
            vx = -vx;

        target.setX((int) (x + vx));
    }
}
