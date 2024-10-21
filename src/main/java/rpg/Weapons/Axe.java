package rpg.Weapons;

import rpg.Monster;
import rpg.Weapon;

public class Axe extends Weapon {
    private static final int DAMAGE = 20;
    private static final String NAME = "AXE";
    private static final double PRICE = 10;
    private static final double DAMAGE_MONSTER_RATIO = 0.5;
    private static final double DAMAGE_OBSTACLE_RATIO = 1.2;


    //constructeur
    public Axe() {
        super(DAMAGE, NAME, PRICE, DAMAGE_MONSTER_RATIO, DAMAGE_OBSTACLE_RATIO);
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
