package supermario.logic;

import supermario.controllers.Loop;
import supermario.models.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyGravityHandler extends Loop {
    private List<EnemyGravityHandler> instances = new CopyOnWriteArrayList<>();
    private GravityItem target;
    private double vy = 0;
    private double g = 0.08;

    public EnemyGravityHandler(GravityItem target) {
        super(60.0);
        for (EnemyGravityHandler instance: instances)
            if (instance.target == target)
                instance.stop();
        instances.removeIf(i -> i.target == target);
        this.target = target;
        instances.add(this);
    }

    @Override
    public void update() {
        if (State.getCurrentGame() == null || !State.getCurrentGame().isRunning())
            return;
        int y = target.getY();
        Rectangle hitBox = target.getHitBox();
        hitBox.translate(0, (int) vy);
        boolean collides = false;
        if (MapHandler.sectionObject != null) {
            for (BlockObject b : MapHandler.sectionObject.blocks) {
                Rectangle blockHitBox = b.getHitBox();
                if (blockHitBox.intersects(hitBox)) {
                    int oc = hitBox.outcode(blockHitBox.getCenterX(), blockHitBox.getCenterY());
                    if (
                            (vy >= 0 && oc == Rectangle2D.OUT_BOTTOM) ||
                                    (vy < 0 && oc == Rectangle2D.OUT_TOP))
                        collides = true;
                }
            }
            for (PipeObject b : MapHandler.sectionObject.pipes) {
                Rectangle blockHitBox = b.getHitBox();
                if (blockHitBox.intersects(hitBox)) {
                    int oc = hitBox.outcode(blockHitBox.getCenterX(), blockHitBox.getCenterY());
                    if (
                            (vy >= 0 && oc == Rectangle2D.OUT_BOTTOM) ||
                                    (vy < 0 && oc == Rectangle2D.OUT_TOP))
                        collides = true;
                }
            }
        }
        if (y + vy >= 20 * 32) {
            collides = true;
        }
        if (collides)
            vy = 0.;
        else {
            target.setY((int) (y + vy));
            vy += g;
        }
    }
}
