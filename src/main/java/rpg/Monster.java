package rpg;

import java.util.Random;

public class Monster extends Breakable{
    private int level;
    private double atk;
    private double def;
    private double speed;
    private String state;
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
        this.state = "";

    }

    public Monster(int lvl) {
        super(generateRandomName(), generateRandomHealth(lvl));
        this.atk = generateRandomAttack(lvl);
        this.def = generateRandomDefense(lvl);
        this.speed = generateRandomSpeed(lvl);
        this.state = "";
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    ///methodes
    public void passTurn() {
        System.out.println("L'adversaire à passé son tour");
    }

    public void attack(Player player) {
        if (this.weapon == null) {
            System.out.println("Degat : " + Math.round((this.atk)*100)/100);
            player.hit(this.atk);
        }
        else {
            player.hit(this.atk * this.weapon.getMonsterRationDamage());
        }
    }

    private static String generateRandomName() {
        String[] possibleNames = {
                "Shadow Fiend", "Dire Wolf", "Cave Troll", "Frost Drake", "Undead Wraith",
                "Forest Ogre", "Fire Imp", "Stone Golem", "Dark Serpent", "Vampire Bat"
        };
        Random random = new Random();
        return possibleNames[random.nextInt(possibleNames.length)];
    }

    // Méthode pour générer des dégâts d'attaque aléatoires en fonction du niveau
    private static double generateRandomAttack(int lvl) {
        Random random = new Random();
        if (lvl < 10) {
            return 10 + (10 * random.nextDouble()); // Entre 10 et 20
        } else if (lvl < 30) {
            return 20 + (20 * random.nextDouble()); // Entre 20 et 40
        }
        return 30 + (30 * random.nextDouble()); // Entre 30 et 60
    }

    // Méthode pour générer des valeurs de défense aléatoires en fonction du niveau
    private static double generateRandomDefense(int lvl) {
        Random random = new Random();
        if (lvl < 10) {
            return 5 + (5 * random.nextDouble()); // Entre 5 et 10
        } else if (lvl < 30) {
            return 10 + (10 * random.nextDouble()); // Entre 10 et 20
        }
        return 15 + (15 * random.nextDouble()); // Entre 15 et 30
    }

    // Méthode pour générer la vitesse aléatoire en fonction du niveau
    private static double generateRandomSpeed(int lvl) {
        Random random = new Random();
        if (lvl < 10) {
            return 1 + (9 * random.nextDouble()); // Entre 1 et 10
        } else if (lvl < 30) {
            return 10 + (5 * random.nextDouble()); // Entre 10 et 15
        }
        return 15 + (30 * random.nextDouble()); // Entre 15 et 45
    }

    // Méthode pour générer les points de vie aléatoires en fonction du niveau
    private static double generateRandomHealth(int lvl) {
        Random random = new Random();
        if (lvl < 10) {
            return 50 + (50 * random.nextDouble()); // Entre 50 et 100
        } else if (lvl < 30) {
            return 100 + (100 * random.nextDouble()); // Entre 100 et 200
        }
        return 200 + (200 * random.nextDouble()); // Entre 200 et 400
    }

    public void showStats() {
        System.out.println("Name: " + getName());
        System.out.println("atk: " + Math.round(getAtk() * 100.0) / 100.0);
        System.out.println("def: " + Math.round(getDef() * 100.0) / 100.0);
        System.out.println("speed: " + Math.round(getSpeed() * 100.0) / 100.0);
        System.out.println("health: " + Math.round(getHealth() * 100.0) / 100.0);
    }
}
