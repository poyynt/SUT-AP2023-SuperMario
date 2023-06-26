package supermario.logic;

import java.awt.Rectangle;

public interface GravityItem {
    int getX();
    int getY();
    void setY(int y);
    Rectangle getHitBox();
}
