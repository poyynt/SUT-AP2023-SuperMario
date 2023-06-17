package supermario.models;

public class EnemyObject {
    public final int x, y;
    public final EnemyType type;

    public EnemyObject(int x, int y, EnemyType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
