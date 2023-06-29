package supermario.logic;

import supermario.controllers.Loop;
import supermario.models.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletMovementHandler extends Loop {
    private List<BulletMovementHandler> instances = new CopyOnWriteArrayList<>();
    private BulletObject target;
    private double vx = 3;

    public BulletMovementHandler(BulletObject target) {
        super(60.0);
        for (BulletMovementHandler instance : instances)
            if (instance.target == target)
                instance.stop();
        instances.removeIf(i -> i.target == target);
        this.target = target;
        instances.add(this);
        if (!MovementHandler.isPlayerFacingRight())
            vx *= -1;
    }

    @Override
    public void update() {
        if (State.getCurrentGame() == null || !State.getCurrentGame().isRunning())
            return;
        int x = target.getX();
        Rectangle hitBox = target.getHitBox();
        hitBox.translate((int) vx, 0);
        boolean collides = false;
        if (MapHandler.sectionObject != null) {
            for (BlockObject b : MapHandler.sectionObject.blocks) {
                Rectangle blockHitBox = b.getHitBox();
                if (blockHitBox.intersects(hitBox)) {
                    target.destroy();
                }
            }
            for (PipeObject b : MapHandler.sectionObject.pipes) {
                Rectangle blockHitBox = b.getHitBox();
                if (blockHitBox.intersects(hitBox)) {
                    target.destroy();
                }
            }
            for (EnemyObject e : MapHandler.sectionObject.enemies) {
                Rectangle enemyHitBox = e.getHitBox();
                if (enemyHitBox.intersects(hitBox)) {
                    e.getHit();
                    target.destroy();
                }
            }
        }
        target.setX((int) (x + vx));
        if (target.getX() - target.getIx() >= 8 * 32)
            target.destroy();
    }
}
