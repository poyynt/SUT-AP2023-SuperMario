package supermario.logic;

import supermario.models.EnemyObject;
import supermario.models.State;

import java.awt.*;

public class GoombaCollisionHandler extends EnemyCollisionHandler {

    public GoombaCollisionHandler(EnemyObject target) {
        super(target);
    }

    @Override
    public void update() {
        if (State.getCurrentGame() == null || !State.getCurrentGame().isRunning())
            return;
        Rectangle hitBox = target.getHitBox();
        Rectangle headHitBox = target.getHeadHitBox();
        Rectangle marioHitBox = State.getCurrentGame().getPlayer().getHitBox();
        if (headHitBox.intersects(marioHitBox))
            target.getKilled();
        else if (hitBox.intersects(marioHitBox)) {
            State.getCurrentGame().getPlayer().getHit();
        }
    }
}
