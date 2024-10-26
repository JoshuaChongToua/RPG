package rpg;

import rpg.Classes.Warrior;
import rpg.Classes.Shooter;
import rpg.Weapons.Axe;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player player;
    private ArrayList<Monster> monsters;
    private Shop shop;
    private GameMap gameMap;


    /// constructeur
    public Game() {
        Monster goblin = new Monster("Gobelin", 2, 3, 3, 4, 5);
        Monster troll = new Monster("Troll", 42, 3, 13, 4, 5);
        Monster loup = new Monster("Loup", 42, 3, 3, 4, 24);
        this.monsters = new ArrayList<>();
        this.monsters.add(goblin);
        this.monsters.add(troll);
        this.monsters.add(loup);
        this.player = new Player();
        this.createPlayer();
        this.shop = new Shop(this.player);
        shop.generateItems();

        this.gameMap = new GameMap(7, 7, this.player, this);
        startGame();
    }

    /// getter setter
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public Player createPlayer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Création du personnage : ");
        String name = sc.nextLine();
        this.player.setName(name);

        System.out.println("Choix de la classe : ");
        System.out.println("1. Guerrier");
        System.out.println("2. tireur");
        int choice = sc.nextInt();

        if (choice == 1) {
            this.player.setAtk(this.player.getAtk() + 12);
            this.player.setDef(this.player.getDef() + 12);
            this.player.setSpeed(this.player.getSpeed() -20);
            this.player.setHealth(this.player.getHealth() + 1000);
            this.player.setMaxHealth(this.player.getHealth());

            this.player.setClasse(new Warrior());
            System.out.println("Tu as choisi la classe Guerrier");

        } else if (choice == 2) {
            this.player.setAtk(this.player.getAtk() + 15);
            this.player.setDef(this.player.getDef() + 6);
            this.player.setSpeed(this.player.getSpeed() + 12);
            this.player.setHealth(this.player.getHealth() + 12);
            this.player.setMaxHealth(this.player.getHealth());

            this.player.setClasse(new Shooter());
            System.out.println("Tu as choisi la classe Tireur");

        }
        this.player.showStats();

        return this.player;
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Aventure");
        System.out.println("2. Inventaire");
        System.out.println("3. Stats");
        System.out.println("4. Boutique");
        System.out.println("5. Quitter la partie");

        String choice = sc.nextLine();

        while (!choice.equals("5")) {
            switch (choice) {
                case "1":
                    System.out.println("C'est parti !!");
                    String monsterChoice;
                    while (true) {
                        int i = 0;
                        for (Monster monster : this.monsters) {
                            if (monster.getHealth() <= 0) {
                                System.out.println("[" + i + "]. " + monster.getName() + "(Déja vaincu)");

                            } else {
                                System.out.println("[" + i + "]. " + monster.getName());
                            }
                            i++;
                        }
                        System.out.println("Qui voulez vous affronter ?  (quitter ? [quit])");
                        monsterChoice = sc.nextLine();

                        if (monsterChoice.equals("quit")) {
                            System.out.println("Retour au menu");
                            break;
                        }

                        try {
                            int index = Integer.parseInt(monsterChoice);
                            if (index >= 0 && index < this.monsters.size()) {
                                System.out.println("Vous avez choisi d'affronter : " + this.monsters.get(index).getName());
                                if (this.monsters.get(index) instanceof Monster) {
                                    System.out.println("Voulez-vous l'affronter ? [oui/non]");
                                    String finalChoice = sc.nextLine();
                                    switch (finalChoice) {
                                        case "oui":
                                            Fight fight = new Fight(this, this.monsters.get(index));
                                            break;

                                        case "non":
                                            break;

                                        default:
                                            System.out.println("Choix invalide. Essayez de nouveau.");
                                            break;
                                    }
                                }
                            } else {
                                System.out.println("Indice invalide. Veuillez entrer un indice entre 0 et " + (this.monsters.size() - 1) + " ou 'quit' pour quitter.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Saisie invalide. Veuillez entrer un indice valide ou 'quit' pour quitter.");
                        }
                    }
                    break;
                case "2":
                    System.out.println("Affichage de l'inventaire...");
                    player.showInventaire();
                    break;
                case "3":
                    System.out.println("Affichage des stats...");
                    player.showStats();
                    break;
                case "4":
                    System.out.println("Ouverture de la boutique...");
                    this.goInShop();
                    break;
                default:
                    System.out.println("Choix invalide. Essayez de nouveau.");
                    break;
            }

            // Réaffiche les options après chaque action
            System.out.println("1. Aventure");
            System.out.println("2. Inventaire");
            System.out.println("3. Stats");
            System.out.println("4. Boutique");
            System.out.println("5. Quitter la partie");
            choice = sc.nextLine();
        }
        System.out.println("Merci d'avoir joué !");
    }


    public void startGame() {
        //menu();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            gameMap.displayMap();
            System.out.println("Déplacez-vous (z/q/s/d) ou 'quit' pour quitter ou 'i' pour ouvrir l'inventaire ou 'c' pour afficher les stats: ");
            String input = scanner.nextLine();

            if (input.equals("quit")) {
                break; // Quitter le jeu
            }

            if (input.equals("c")) {
                this.player.showStats();
            }

            boolean gameEnded = gameMap.movePlayer(input); // Déplacer le joueur
            if (gameEnded) {
                gameMap.displayMap();
                break;
            }
        }
        System.out.println("Merci d'avoir joué !");
    }

    public void goInShop() {
        Scanner scanner = new Scanner(System.in);
        int i = 0;

        if (this.shop.getItems().isEmpty()) {
            System.out.println("Le magasin est vide");
        } else {

            for (Item item : this.shop.getItems()) {
                System.out.println("[" + i + "] " + item.toString() + "\n" + item.asciiArt());
                i++;
            }

            // Boucle pour saisir une commande
            String choice;
            while (true) {
                System.out.println("Quel objet souhaitez-vous acheter ? (Entrez l'indice ou 'quit' pour quitter) :");
                choice = scanner.nextLine();

                if (choice.equals("quit")) {
                    System.out.println("Vous avez quitté le magasin.");
                    break;
                }

                // Vérifier si l'entrée est un nombre valide correspondant à un item
                try {
                    int index = Integer.parseInt(choice);
                    if (index >= 0 && index < this.shop.getItems().size()) {
                        if (player.getMoney() < this.shop.getItems().get(index).getPrice()) {
                            System.out.println("Vous ne possèdez pas assez d'argent");
                        } else {
                            System.out.println("Vous avez choisi l'objet : " + this.shop.getItems().get(index));
                            System.out.println("Etês-vous sûr de vouloir l'acheter ? ");
                            choice = scanner.nextLine();
                            if (choice.equals("oui") || choice.equals("o")) {
                                Item item = this.shop.getItems().get(index);
                                player.setMoney(-item.getPrice());
                                player.addItemToInventaire(item);
                                this.shop.getItems().remove(index);
                            }
                            break;
                        }
                    } else {
                        System.out.println("Indice invalide. Veuillez entrer un indice entre 0 et " + (this.shop.getItems().size() - 1) + " ou 'quit' pour quitter.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Saisie invalide. Veuillez entrer un indice valide ou 'quit' pour quitter.");
                }
            }
        }
    }

    public void resetHealth() {
        // Réinitialise les points de vie du joueur
        this.player.setHealth(this.player.getMaxHealth());

        // Réinitialise les points de vie de chaque monstre
        for (Monster monster : this.monsters) {
            monster.setHealth(monster.getMaxHealth());
        }

        System.out.println("Points de vie réinitialisés pour tous les personnages !");
        System.out.println("Points de vie du joueur : " + this.player.getHealth());
//        for (Monster monster : this.monsters) {
//            System.out.println("Points de vie du monstre " + monster.getName() + " : " + monster.getHealth());
//        }
    }

}
