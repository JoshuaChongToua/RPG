package rpg;

import rpg.Classes.Mage;
import rpg.Items.Potion;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Player {
    private String name;
    private int level;
    private Classe classe;
    private Weapon weapon;
    private ArrayList<Item> inventaire;
    private double atk;
    private double def;
    private double speed;
    private double health;
    private double maxHealth = this.health;
    private double money;
    private double xp;
    private double maxXp;
    private double stamina;
    private double maxStamina;
    private String state;


    private int x = 0;
    private int y = 0;

    //constructeur
    public Player(String name) {
        this.name = name;
        this.level = 1;
        this.money = 100;
        this.xp = 0;
        this.inventaire = new ArrayList<>();
        this.weapon = null;
        this.health = 20;
        this.maxXp = 500;
        this.stamina = 100;
        this.state = "";
    }

    public Player() {
        this.level = 1;
        this.money = 100;
        this.xp = 0;
        this.inventaire = new ArrayList<>();
        this.weapon = null;
        this.health = 20;
        this.stamina = 100;
        this.maxXp = 500;
        this.state = "";

    }

    //getter setter


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public ArrayList<Item> getInventaire() {
        return inventaire;
    }

    public void setInventaire(ArrayList<Item> inventaire) {
        this.inventaire = inventaire;
    }

    public double getAtk() {
        return atk;
    }

    public void setAtk(double atk) {
        this.atk += atk;
    }

    public double getDef() {
        return def;
    }

    public void setDef(double def) {
        this.def += def;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed += speed;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money += money;
    }

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp += xp;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getStamina() {
        return stamina;
    }

    public void setStamina(double stamina) {
        this.stamina = stamina;
    }

    public double getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(double maxStamina) {
        this.maxStamina = maxStamina;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getMaxXp() {
        return maxXp;
    }

    public void setMaxXp() {
        Random random = new Random();

        // Génère un nombre aléatoire entre 500 et 700
        double augmentation = 500 + random.nextInt(201); // 201 pour inclure 700

        // Ajoute l'augmentation à maxXp
        this.maxXp += augmentation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // Methodes


    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void showStats() {
        System.out.println("Name: " + getName());
        System.out.println("Money: " + Math.round(getMoney() * 100.0) / 100.0);
        System.out.println("Level: " + getLevel());
        System.out.println("Classe: " + getClasse());
        System.out.println("atk: " + Math.round(getAtk() * 100.0) / 100.0);
        System.out.println("def: " + Math.round(getDef() * 100.0) / 100.0);
        System.out.println("speed: " + Math.round(getSpeed() * 100.0) / 100.0);
        System.out.println("health: " + Math.round(getHealth() * 100.0) / 100.0);
        System.out.println("xp: " + Math.round(getXp() * 100.0) / 100.0);
        System.out.println("MaxXp: " + Math.round(getMaxXp() * 100.0) / 100.0);
    }

    public void addItemToInventaire(Item item) {
        inventaire.add(item);
        System.out.println("Item ajouté à l'inventaire : " + item.toString());
    }

    public void showInventaire() {
        Scanner sc = new Scanner(System.in);
        if (this.inventaire.isEmpty()) {
            System.out.println("L'inventaire est vide");
        } else {
            int i = 0;
            for (Item item : this.inventaire) {
                System.out.println("[" + i + "] " + item.showItem());
                i++;
            }
            // Boucle pour saisir une commande
            String choice;
            while (true) {
                System.out.println("Quel objet souhaitez-vous utiliser / equipé ?  (quitter ? [quit])");
                choice = sc.nextLine();

                if (choice.equals("quit")) {
                    System.out.println("Vous avez quitté l'inventaire.");
                    break;
                }

                try {
                    int index = Integer.parseInt(choice);
                    if (index >= 0 && index < this.inventaire.size()) {
                        System.out.println("Vous avez choisi l'objet : " + this.inventaire.get(index).toString());
                        if (this.inventaire.get(index) instanceof Weapon) {
                            System.out.println("Voulez-vous l'équiper ? [oui / non]");
                            String finalChoice = sc.nextLine();
                            switch (finalChoice) {
                                case "oui":
                                    this.use((Weapon) this.inventaire.get(index));
                                    System.out.println("Vous avez équipé " + this.inventaire.get(index).getName());
                                    break;

                                case "non":
                                    break;

                                default:
                                    System.out.println("Choix invalide. Essayez de nouveau.");
                                    break;
                            }
                        } else if (this.inventaire.get(index) instanceof Potion) {
                            System.out.println("Voulez-vous l'utiliser ? [oui / non]");
                            String finalChoice = sc.nextLine();
                            switch (finalChoice) {
                                case "oui":
                                    this.use((Potion) this.inventaire.get(index));
                                    System.out.println("Vous avez bu la " + this.inventaire.get(index).getName());
                                    this.inventaire.remove(this.inventaire.get(index));
                                    break;

                                case "non":
                                    break;

                                default:
                                    System.out.println("Choix invalide. Essayez de nouveau.");
                                    break;
                            }
                        } else {
                            System.out.println("Voulez-vous l'utiliser ? ");
                            String finalChoice = sc.nextLine();
                            switch (finalChoice) {
                                case "oui":
                                    break;

                                case "non":
                                    break;

                                default:
                                    System.out.println("Choix invalide. Essayez de nouveau.");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Indice invalide. Veuillez entrer un indice entre 0 et " + (this.inventaire.size() - 1) + " ou 'quit' pour quitter.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Saisie invalide. Veuillez entrer un indice valide ou 'quit' pour quitter.");
                }
            }
        }
    }

    public void use(Weapon weapon) {
        this.setWeapon(weapon);
        this.setAtk(this.atk + weapon.damage);
    }

    public void use(Potion potion) {
        switch (potion.getEffectType()) {
            case HEALTH:
                this.health += potion.getValue();
                System.out.println("Utilisation d'une potion de soin +" + potion.getValue() + " pv");
                System.out.println(this.health);
                break;
            case SPEED:
                this.speed += potion.getValue();
                System.out.println("Utilisation d'une potion de vitesse +" + potion.getValue() + " en vitesse");
                System.out.println(this.speed);
                break;
            case ATTACK:
                this.atk += potion.getValue();
                System.out.println("Utilisation d'une potion d'attaque +" + potion.getValue() + " en degats");
                System.out.println(this.atk);
                break;
            default:
                throw new IllegalArgumentException("Type d'effet de potion inconnu: " + potion.getEffectType());
        }
    }

    public boolean specialAttack(Monster monster) {
        if (this.weapon == null) {
            System.out.println("Pas d'arme equipé");
            return false;
        } else if (this.stamina < 25) {
            System.out.println("Pas assez de stamina");
            return false;
        } else {
            monster.hit(this.atk * this.weapon.getSpeAtkRatio());
            this.stamina -= 25;
            return true;
        }
    };

    public void attack(Monster monster) {
        if (this.weapon == null) {
            monster.hit(this.atk);
        } else {
            monster.hit(this.atk * this.weapon.getMonsterRationDamage());
        }
        this.stamina += 5;
        if (Objects.equals(this.classe.getName(), "Mage")) {
            Mage mage = (Mage) this.getClasse();
            if (mage.getMana()<100) {
                mage.setMana(mage.getMana() + 5);
            }
        }

    }

    public void attack(Obstacle obstacle) {
        if (this.weapon == null) {
            obstacle.hit(this.atk);
        } else {
            obstacle.hit(this.atk * this.weapon.getMonsterRationDamage());
        }
        this.stamina += 5;
        if (Objects.equals(this.classe.getName(), "Mage")) {
            Mage mage = (Mage) this.getClasse();
            if (mage.getMana()<100) {
                mage.setMana(mage.getMana() + 5);
            }
        }
    }

    public void hit(double damage) {
        this.health -= damage;
    }

    public void passTurn() {
        System.out.println("Vous avez passez votre tour");
    }

    public void xpManager (double xp) {
        this.xp += xp;
        System.out.println("Vous avez gagné " + xp + " points d'expérience");
        if (this.xp >= this.maxXp) {
            double rest = this.xp - this.maxXp;
            this.level ++;
            this.xp = rest;
            this.setMaxXp();
            System.out.println("Félicitation vous êtes passé au niveau : " + this.level);
        }
    }



}
