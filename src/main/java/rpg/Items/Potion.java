package rpg.Items;

import rpg.Item;
import rpg.Player;

public class Potion extends Item {
    public enum PotionEffectType {
        HEALTH, ATTACK, SPEED
    }

    private int value; // La quantité d'effet que la potion donne
    private PotionEffectType effectType; // Le type d'effet de la potion

    public Potion(String name, double price, int value, PotionEffectType effectType) {
        super(name, price);
        this.value = value;
        this.effectType = effectType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public PotionEffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(PotionEffectType effectType) {
        this.effectType = effectType;
    }

    @Override
    public String asciiArt() {
        return "       _____\n" +
                "     `.___,'\n" +
                "      (___)\n" +
                "      <   >\n" +
                "       ) (\n" +
                "      /`-.\\\n" +
                "     /     \\\n" +
                "    / _    _\\\n" +
                "   :,' `-.' `:\n" +
                "   |         |\n" +
                "   :         ;\n" +
                "    \\       /\n" +
                "     `.___.' ";
    }

    @Override
    public String showItem() {
        return "Potion de " + effectType + " qui augmente de " + value;
    }

    @Override
    public String toStringInventaire() {
        return this.name + " (" + effectType + " + " + value + ")";
    }

}
