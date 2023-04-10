package ir.sharif.math.ap2023.supermario.models;

public class GameCharacter {
    private final String name;
    private final int jumpSpeed;
    private final int moveSpeed;
    private final int coinRadiusMultiplier;
    private final int shotSpeed;
    private final int price;

    public GameCharacter(String name, int jumpSpeed, int moveSpeed, int coinRadiusMultiplier, int shotSpeed, int price) {
        this.name = name;
        this.jumpSpeed = jumpSpeed;
        this.moveSpeed = moveSpeed;
        this.coinRadiusMultiplier = coinRadiusMultiplier;
        this.shotSpeed = shotSpeed;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getJumpSpeed() {
        return jumpSpeed;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public int getCoinRadiusMultiplier() {
        return coinRadiusMultiplier;
    }

    public int getShotSpeed() {
        return shotSpeed;
    }

    public int getPrice() {
        return price;
    }
}
