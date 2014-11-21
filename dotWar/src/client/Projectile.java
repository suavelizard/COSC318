package client;

/**
 * Created by Zane on 2014-11-06.
 */
public class Projectile {
    private Position position;
    private double damage;
    private Position targetPos;
    private int moveSpeed;

    public Position getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(Position targetPos) {
        this.targetPos = targetPos;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

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

    public Projectile(Position position, Position targetPos, int moveSpeed, double damage) {
        this.setPosition(position);
        this.setTargetPos(targetPos);
        this.setMoveSpeed(moveSpeed);
        this.setDamage(damage);
    }

    public void move() {
        double differenceX = position.getX() - targetPos.getX();
        double differenceY = position.getY() - targetPos.getY();

        double angle = (float)Math.atan2(differenceY, differenceX) * 180 / Math.PI;

        position.setX(position.getX()+ Math.cos(angle * Math.PI/180) * moveSpeed);
        position.setY(position.getY()+ Math.sin(angle * Math.PI /180) * moveSpeed);
    }
}
