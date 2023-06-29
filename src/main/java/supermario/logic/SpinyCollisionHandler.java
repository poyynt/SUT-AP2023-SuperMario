package supermario.logic;

import supermario.models.EnemyObject;
import supermario.models.State;

import java.awt.*;

public class SpinyCollisionHandler extends EnemyCollisionHandler {
    public SpinyCollisionHandler(EnemyObject target) {
        super(target);
    }

    @Override
    public void update() {
        if (State.getCurrentGame() == null || !State.getCurrentGame().isRunning())
            return;
        Rectangle hitBox = target.getHitBox();
        Rectangle marioHitBox = State.getCurrentGame().getPlayer().getHitBox();
        if (hitBox.intersects(marioHitBox))
            State.getCurrentGame().getPlayer().getHit();
    }
}
