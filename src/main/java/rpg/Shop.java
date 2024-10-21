package rpg;

import rpg.Items.Potion;
import rpg.Weapons.Axe;
import rpg.Weapons.Hammer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Shop {
    protected ArrayList<Item> items;
    private Random random = new Random();
    private int x;
    private int y;

    //Constructeur
    public Shop(){
        items = new ArrayList<>();
    }


    //getter setter
    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> weapons) {
        this.items = weapons;
    }


    //Methode
    public void addShop(Item item) {
        this.items.add(item);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void generateItems() {
        Random random = new Random();
        int count = random.nextInt(10)+1;
        for (int i = 0; i < count; i++) {
            int randomChoice = random.nextInt(3); // 0: Axe, 1: Hammer, 2: Potion

            switch (randomChoice) {
                case 0:
                    items.add(new Axe()); // Créer une hache
                    break;
                case 1:
                    items.add(new Hammer()); // Créer un marteau
                    break;
                case 2:
                    // Générer une potion avec un effet aléatoire
                    Potion.PotionEffectType randomEffect = Potion.PotionEffectType.values()[random.nextInt(Potion.PotionEffectType.values().length)];
                    int potionValue = random.nextInt(20) + 1; // Générer une valeur aléatoire pour la potion
                    items.add(new Potion("Potion", 10.0, potionValue, randomEffect)); // Créer une potion avec un effet aléatoire
                    break;
            }
        }
    }




}
