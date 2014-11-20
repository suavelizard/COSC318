package client;

/**
 * Created by Zane on 2014-11-06.
 */
public class Weapon {
    private double damage;
    private double rate;
    private int projectileSpeed;

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(int projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public Weapon() {
        this.setDamage(1);
        this.setProjectileSpeed(50);
        this.setRate(2);
    }

    public Weapon(double damage, int projectileSpeed, double rate ) {
        this.setDamage(damage);
        this.setProjectileSpeed(projectileSpeed);
        this.setRate(rate);
    }
}
