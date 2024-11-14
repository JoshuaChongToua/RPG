package rpg.Weapons;

import rpg.Weapon;

import java.util.Random;

public class Spellbook extends Weapon {

    //constructeur
    public Spellbook(String name, double damage, double price, double monsterDamageRatio, double obstacleDamageRatio) {
        // Appel du constructeur de la classe parente Weapon
        super(name, damage, price, monsterDamageRatio, obstacleDamageRatio);
    }

    public Spellbook(int lvl) {
        // Appel au constructeur de la superclasse avec des valeurs aléatoires
        super(
                generateRandomName(),
                generateRandomDamage(lvl),
                generateRandomPrice(lvl),
                generateRandomMonsterDamageRatio(lvl),
                generateRandomObstacleDamageRatio(lvl)
        );
    }

    // Méthode pour générer des noms aléatoires
    private static String generateRandomName() {
        String[] spellNames = {
                "Fireball Blast", "Ice Spear", "Lightning Bolt", "Shadow Grasp", "Healing Light",
                "Mystic Barrier", "Arcane Surge", "Inferno Blaze", "Frost Nova", "Thunderstorm",
                "Venomous Fang", "Soul Drain", "Flame Whirlwind", "Astral Projection", "Dark Rift",
                "Wind Slash", "Earthquake Stomp", "Holy Smite", "Bloodlust Frenzy", "Celestial Ray"
        };
        Random random = new Random();
        return spellNames[random.nextInt(spellNames.length)];
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



    //methode

    @Override
    public String asciiArt() {
        return "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⣀⣤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⡟⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠘⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠈⠑⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣶⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣿⣿⣿⠛⠁⠀⠀⠀⠀⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠙⢻⡇⠀⠀⠀⠀⠀⠀⠀⠐⠻⠏⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⣀⡀⢠⣴⣶⣿⣿⣿⣿⣿⡆⢰⣶⠶⠶⠶⠶⠦⣤⡄⢀⣀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⣿⠁⣼⣿⣿⣿⣿⣿⣿⣿⡇⢸⣿⣶⣶⣶⣶⣶⣿⣧⠈⣿⠀⠀⠀⠀\n" +
                "⠀⠀⠀⢠⡍⢀⣿⣿⣿⣿⣿⣿⣿⣿⡇⢸⣿⠛⠛⠛⠛⠛⠻⣿⡀⢻⡇⠀⠀⠀\n" +
                "⠀⠀⠀⠛⠃⣸⣿⣿⣿⣿⣿⣿⣿⣿⡇⢸⣿⠛⠛⠛⣿⡟⠛⢻⣇⠘⣷⠀⠀⠀\n" +
                "⠀⠀⢰⡟⢀⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⢸⣿⠛⠛⠛⠛⠛⠛⠛⣿⡀⢻⡄⠀⠀\n" +
                "⠀⠀⣾⡇⠘⠟⠛⠛⠉⣉⣉⣉⡉⠛⠃⠘⠛⠛⠛⠛⠛⠛⠛⠲⠿⠃⢸⣧⠀⠀\n" +
                "⠀⢀⣉⣁⣀⣀⣉⣉⣉⣉⣉⣉⣉⣉⣁⣈⣉⣉⣉⣉⣉⣉⣁⣀⣀⣀⣈⣉⡀⠀\n" +
                "⠀⠘⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠃⠀";
    }


}
