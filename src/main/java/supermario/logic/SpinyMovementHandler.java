package supermario.logic;

import supermario.models.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SpinyMovementHandler extends EnemyMovementHandler {
    private double vx = 1;
    private double ax = 0;

    public SpinyMovementHandler(EnemyObject target) {
        super(target);
    }

    @Override
    public void update() {
        if (State.getCurrentGame() == null || !State.getCurrentGame().isRunning())
            return;
        int x = target.getX();
        int distance = State.getCurrentGame().getPlayer().getX() - x;
        int vDistance = State.getCurrentGame().getPlayer().getY() - target.getY();
        if (Math.abs(distance) >= 4 * 32 || Math.abs(vDistance) >= 4) {
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
                for (PipeObject b : MapHandler.sectionObject.pipes) {
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
                for (PipeObject b : MapHandler.sectionObject.pipes) {
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
        }
        else {
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
                for (PipeObject b : MapHandler.sectionObject.pipes) {
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
            if (collides)
                ax = vx = 0;
            if (distance > 0)
                ax = 1;
            else
                ax = -1;
            vx += ax;
        }
        target.setX((int) (target.getX() + vx));
    }
}
