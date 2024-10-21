package rpg;

public class Monster extends Breakable{
    private int level;
    private double atk;
    private double def;
    private double speed;
    private Weapon weapon;
    private int x;
    private int y;

    public static final double LIFE = 50;

    ///constructeur
    public Monster(String name) {
        super(name,LIFE);
    }

    public Monster(String name, double health, int level, double atk, double def, double speed) {
        super(name,health);
        this.level = level;
        this.atk = atk;
        this.def = def;
        this.speed = speed;
        this.weapon = null;
    }

    ///getter setter
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getAtk() {
        return atk;
    }

    public void setAtk(double atk) {
        this.atk = atk;
    }

    public double getDef() {
        return def;
    }

    public void setDef(double def) {
        this.def = def;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    ///methodes
    public void passTurn() {
        System.out.println("L'adversaire à passé son tour");
    }

    public void attack(Player player) {
        if (this.weapon == null) {
            player.hit(this.atk);
        }
        else {
            player.hit(this.atk * this.weapon.getMonsterRationDamage());
        }
    }
}
