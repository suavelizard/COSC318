package client;

/**
 * Created by Zane on 2014-11-06.
 */
public class Projectile {
    private Position position;
    private int moveSpeed;

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    private double damage;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public Projectile() {
        this.setPosition(new Position(400.0,400.0));
        this.setMoveSpeed(50);
        this.setDamage(1);
    }

    public Projectile(Position position, int moveSpeed, double damage) {
        this.setPosition(position);
        this.setMoveSpeed(moveSpeed);
        this.setDamage(damage);
    }
}
