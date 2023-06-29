package supermario.models;

import java.awt.*;

public class EnemyObject {
    public final int x, y;
    public final EnemyType type;

    public EnemyObject(int x, int y, EnemyType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Rectangle getHitBox() {
        return new Rectangle(x, y, 32, 32);
    }

    public void getHit() {
        // TODO
    }
}
