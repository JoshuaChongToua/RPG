package rpg.Weapons;

import rpg.Monster;
import rpg.Weapon;

import java.util.Random;

public class Axe extends Weapon {
    
    //constructeur
    public Axe(String name, double damage, double price, double monsterDamageRatio, double obstacleDamageRatio, String specialAttackName, double specialAttackRatio) {
        // Appel du constructeur de la classe parente Weapon
        super(name, damage, price, monsterDamageRatio, obstacleDamageRatio, specialAttackName, specialAttackRatio);
    }

    public Axe(int lvl) {
        // Appel au constructeur de la superclasse avec des valeurs aléatoires
        super(
                generateRandomName(),
                generateRandomDamage(lvl),
                generateRandomPrice(lvl),
                generateRandomMonsterDamageRatio(lvl),
                generateRandomObstacleDamageRatio(lvl),
                generateRandomSpecialAttackName(),
                generateRandomSpecialAttackRatio()
        );
    }

    // Méthode pour générer des noms aléatoires
    private static String generateRandomName() {
        String[] possibleNames = {"Heavy Hammer", "Thunder Smash", "Iron Crusher", "Stone Breaker", "War Hammer"};
        Random random = new Random();
        return possibleNames[random.nextInt(possibleNames.length)];
    }

    // Méthode pour générer des dégâts aléatoires
    private static double generateRandomDamage(int lvl) {
        Random random = new Random();
        if (lvl < 10) {
            return 15 + (20 * random.nextDouble());
        } else if (lvl < 30) {
            return 20 + (30 * random.nextDouble());
        }
        return 30 + (40 * random.nextDouble());
    }

    // Méthode pour générer un prix aléatoire
    private static double generateRandomPrice(int lvl) {
        Random random = new Random();
        if (lvl < 10) {
            return 75 + (45 * random.nextDouble());
        } else if (lvl < 30) {
            return 120 + (80 * random.nextDouble());
        }
        return 200 + (300 * random.nextDouble()); // Par exemple entre 400 et 1500
    }

    // Méthode pour générer le ratio de dégâts sur les monstres
    private static double generateRandomMonsterDamageRatio(int lvl) {
        Random random = new Random();
        if (lvl < 10) {
            return 1 + (0.5 * random.nextDouble());
        } else if (lvl < 30) {
            return 1.5 + (0.5 * random.nextDouble());
        }
        return 2 + (0.7 * random.nextDouble());
    }

    // Méthode pour générer le ratio de dégâts sur les obstacles
    private static double generateRandomObstacleDamageRatio(int lvl) {
        Random random = new Random();
        if (lvl < 10) {
            return 1 + (1.5 * random.nextDouble());
        } else if (lvl < 30) {
            return 1.5 + (2 * random.nextDouble());
        }
        return 2 + (2.7 * random.nextDouble());
    }

    // Méthode pour générer le nom de l'attaque spéciale
    private static String generateRandomSpecialAttackName() {
        String[] possibleSpecialAttacks = {"Earthquake", "Skyfall", "Meteor Strike", "Crushing Blow", "Shattering Impact"};
        Random random = new Random();
        return possibleSpecialAttacks[random.nextInt(possibleSpecialAttacks.length)];
    }

    // Méthode pour générer le ratio de l'attaque spéciale
    private static double generateRandomSpecialAttackRatio() {
        Random random = new Random();
        return 2.5 + (0.7 * random.nextDouble()); // Par exemple entre 1.5 et 3.0
    }


    //methode

    @Override
    public String asciiArt() {
        return "  ,:\\      /:.\n" +
                " //  \\_()_/  \\\\\n" +
                "||   |    |   ||\n" +
                "||   |    |   ||\n" +
                "||   |____|   ||\n" +
                " \\\\  / || \\  //\n" +
                "  `:/  ||  \\;'\n" +
                "       ||\n" +
                "       ||\n" +
                "       XX\n" +
                "       XX\n" +
                "       XX\n" +
                "       XX\n" +
                "       OO\n" +
                "       `'";
    }


}
