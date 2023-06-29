package supermario.models;

import supermario.controllers.AudioController;
import supermario.logic.*;

import java.awt.*;

public class EnemyObject implements GravityItem {
    public int x, y;
    public final EnemyType type;
    private EnemyGravityHandler gravityHandler;
    private EnemyMovementHandler movementHandler;
    private EnemyCollisionHandler collisionHandler;
    private transient int phase = 1;

    public EnemyObject(int x, int y, EnemyType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.gravityHandler = new EnemyGravityHandler(this);
        this.gravityHandler.start();
        if (type == EnemyType.GOOMBA || type == EnemyType.KOOPA) {
            this.movementHandler = new GoombaMovementHandler(this);
            this.movementHandler.start();
        }
        switch (type) {
            case GOOMBA -> this.collisionHandler = new GoombaCollisionHandler(this);
            case KOOPA -> this.collisionHandler = new KoopaCollisionHandler(this);
            case SPINY -> this.collisionHandler = new SpinyCollisionHandler(this);
        }
        if (this.collisionHandler != null) {
            this.collisionHandler.start();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public Rectangle getHitBox() {
        return new Rectangle(x, y, 32, 32);
    }

    public Rectangle getHeadHitBox() {
        return new Rectangle(x + 4, y, 24, 16);
    }

    public void getHit() {
        AudioController.playWavAudioOnChannel("killedEnemy", "Killed", 0);
        switch (type) {
            case GOOMBA -> State.getCurrentGame().addScore(1);
            case KOOPA -> State.getCurrentGame().addScore(2);
            case SPINY -> State.getCurrentGame().addScore(3);
        }
        switch (type) {
            case GOOMBA, KOOPA, SPINY -> State.getCurrentGame().addCoins(3);
        }
        MapHandler.sectionObject.enemies.remove(this);
        if (this.movementHandler != null)
            this.movementHandler.stop();
        this.gravityHandler.stop();
    }

    public void getKilled() {
        AudioController.playWavAudioOnChannel("killedEnemy", "Killed", 0);
        switch (type) {
            case GOOMBA -> {
                State.getCurrentGame().addScore(1);
                State.getCurrentGame().addCoins(3);
                MapHandler.sectionObject.enemies.remove(this);
                if (this.movementHandler != null)
                    this.movementHandler.stop();
                if (this.collisionHandler != null)
                    this.collisionHandler.stop();
                this.gravityHandler.stop();
            }
        }
    }
}
