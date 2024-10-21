package rpg;

public abstract class Breakable {
    private double health;
    private double maxHealth;
    private String name;

    public Breakable(String name,double health) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
    }

    public void hit(double damage) {
        this.health -= damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public abstract void attack(Player player);

    public abstract void passTurn();
}
