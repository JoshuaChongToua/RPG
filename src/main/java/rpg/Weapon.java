package rpg;


public abstract class Weapon extends Item {
    protected double damage;
    protected double monsterDamageRatio;
    protected double obstacleDamageRatio;



    //constructeur
    public Weapon(double damage, String name, double price, double monsterDamageRatio, double obstacleDamageRatio) {
        super(name, price);
        this.damage = damage;
        this.monsterDamageRatio = monsterDamageRatio;
        this.obstacleDamageRatio = obstacleDamageRatio;
    }

    //getter setter
    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    //methode

    public double getMonsterRationDamage() {
        return this.monsterDamageRatio;
    }
    public double getObstacleRationDamage() {
        return this.obstacleDamageRatio;
    }

    @Override
    public String toString() {
        return this.name +
                "\n - damage : " + this.damage +
                "\n - prix : " + this.price + "$";

    }

    @Override
    public String toStringInventaire() {
        return this.name;
    }

    @Override
    public String showItem() {
        return this.name +
                "\n - damage : " + this.damage+ "\n" +
                this.asciiArt();
    }
}
