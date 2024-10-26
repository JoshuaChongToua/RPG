package rpg;


public abstract class Weapon extends Item {
    protected double damage;
    protected double monsterDamageRatio;
    protected double obstacleDamageRatio;
    private String speAtkName;
    private double speAtkRatio;


    //constructeur
    public Weapon(String name, double damage,  double price, double monsterDamageRatio, double obstacleDamageRatio, String speAtkName, double speAtkRatio) {
        super(name, price);
        this.damage = damage;
        this.monsterDamageRatio = monsterDamageRatio;
        this.obstacleDamageRatio = obstacleDamageRatio;
        this.speAtkName = speAtkName;
        this.speAtkRatio = speAtkRatio;
    }

    //getter setter
    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public String getSpeAtkName() {
        return speAtkName;
    }

    public void setSpeAtkName(String speAtkName) {
        this.speAtkName = speAtkName;
    }

    public double getSpeAtkRatio() {
        return speAtkRatio;
    }

    public void setSpeAtkRatio(double speAtkRatio) {
        this.speAtkRatio = speAtkRatio;
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
