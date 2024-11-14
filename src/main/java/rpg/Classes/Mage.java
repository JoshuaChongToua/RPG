package rpg.Classes;

import rpg.Classe;
import rpg.Monster;

import java.util.Random;

public class Mage extends Classe {
    private int mana = 100;

    public Mage() {
        super("Mage");
    }

    public boolean burn(Monster monster) {
        if (this.mana >= 25) {
            this.mana -= 25;
            Random rand = new Random();
            int random = rand.nextInt(3);
            System.out.println("Vous essayer de brulé l'ennemi");
            if (random == 1) {
                monster.setState("burn");
                System.out.println("Ennemi brulé !");
            } else {
                System.out.println("Raté !");
            }
            return true;
        }
        System.out.println("Mana insufisant");
        return false;

    }

    public boolean paralyse(Monster monster) {
        if (this.mana >= 25) {
            this.mana -= 25;
            Random rand = new Random();
            int random = rand.nextInt(3);
            System.out.println("Vous essayer de paralyser l'ennemi");
            if (random == 1) {
                monster.setState("paralyse");
                System.out.println("Ennemi paralisé !");
            } else {
                System.out.println("Raté !");
            }
            return true;
        }
        System.out.println("Mana insufisant");
        return false;

    }

    public boolean poison(Monster monster) {
        if (this.mana >= 25) {
            this.mana -= 25;
            Random rand = new Random();
            int random = rand.nextInt(3);
            System.out.println("Vous essayer d'empoisonner l'ennemi");
            if (random == 1) {
                monster.setState("poison");
                System.out.println("Ennemi empoisonné !");
            } else {
                System.out.println("Raté !");
            }
            return true;
        }
        System.out.println("Mana insufisant");
        return false;

    }

    public boolean freeze(Monster monster) {
        if (this.mana >= 25) {
            this.mana -= 25;
            Random rand = new Random();
            int random = rand.nextInt(3);
            System.out.println("Vous essayer de geler l'ennemi");
            if (random == 1) {
                monster.setState("freeze");
                System.out.println("Ennemi gelé !");
            } else {
                System.out.println("Raté !");
            }
            return true;
        }
        System.out.println("Mana insufisant");
        return false;

    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
}
