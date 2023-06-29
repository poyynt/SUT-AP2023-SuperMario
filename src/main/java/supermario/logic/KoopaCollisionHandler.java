package supermario.logic;

import supermario.controllers.GameTimer;
import supermario.models.EnemyObject;
import supermario.models.State;

import java.awt.*;
import java.util.TimerTask;

public class KoopaCollisionHandler extends EnemyCollisionHandler {

    public KoopaCollisionHandler(EnemyObject target) {
        super(target);
    }

    @Override
    public void update() {
        if (State.getCurrentGame() == null || !State.getCurrentGame().isRunning())
            return;
        Rectangle hitBox = target.getHitBox();
        Rectangle headHitBox = target.getHeadHitBox();
        Rectangle marioHitBox = State.getCurrentGame().getPlayer().getHitBox();
        if (headHitBox.intersects(marioHitBox)) {
            if (target.getPhase() == 1) {
                target.setPhase(2);
                new GameTimer(new TimerTask() {
                    @Override
                    public void run() {
                        target.setPhase(1);
                    }
                }, 3).start();
            }
            else
                target.getKilled();
        }
        else if (hitBox.intersects(marioHitBox)) {
            State.getCurrentGame().getPlayer().getHit();
        }
    }
}
