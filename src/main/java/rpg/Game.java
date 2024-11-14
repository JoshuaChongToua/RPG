package rpg;

import rpg.Classes.Mage;
import rpg.Classes.Warrior;
import rpg.Classes.Shooter;
import rpg.Items.Potion;
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
        this.player = new Player();
        this.createPlayer();
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

        boolean validChoice = false;
        while (!validChoice) {
            System.out.println("Choix de la classe : ");
            System.out.println("1. Guerrier");
            System.out.println("2. tireur");
            System.out.println("3. Mage");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    this.player.setAtk(this.player.getAtk() + 12);
                    this.player.setDef(this.player.getDef() + 12);
                    this.player.setSpeed(this.player.getSpeed() + 5);
                    this.player.setHealth(this.player.getHealth() + 35);
                    this.player.setMaxHealth(this.player.getHealth());

                    this.player.setClasse(new Warrior());
                    System.out.println("Tu as choisi la classe Guerrier");
                    validChoice = true;
                    break;
                case "2":
                    this.player.setAtk(this.player.getAtk() + 15);
                    this.player.setDef(this.player.getDef() + 6);
                    this.player.setSpeed(this.player.getSpeed() + 12);
                    this.player.setHealth(this.player.getHealth() + 25);
                    this.player.setMaxHealth(this.player.getHealth());

                    this.player.setClasse(new Shooter());
                    System.out.println("Tu as choisi la classe Tireur");
                    validChoice = true;
                    break;
                case "3":
                    this.player.setAtk(this.player.getAtk() + 15);
                    this.player.setDef(this.player.getDef() + 6);
                    this.player.setSpeed(this.player.getSpeed() + 27);
                    this.player.setHealth(this.player.getHealth() + 22);
                    this.player.setMaxHealth(this.player.getHealth());

                    this.player.setClasse(new Mage());
                    validChoice = true;
                    System.out.println("Tu as choisi la classe Mage");
                    break;

                default:
                    System.out.println("Choix invalide");
                    break;
            }
        }



        this.player.showStats();

        return this.player;
    }

    public void startGame() {
        this.monsters = new ArrayList<>();
        this.shop = new Shop(this.player);
        shop.generateItems();
        this.generateMonster();

        this.gameMap = new GameMap(7, 7, this.player, this);
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
                if (item instanceof Potion potion) {
                    System.out.println("[" + i + "] " + item.toStringInventaire() + "\n" + item.asciiArt());
                } else {
                    System.out.println("[" + i + "] " + item.toString() + "\n" + item.asciiArt());
                }
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
                            System.out.println("Etês-vous sûr de vouloir l'acheter ? [oui / non]");
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

    public void generateMonster() {
        Random random = new Random();
        int count = 3 +random.nextInt(4);
        for (int i = 0; i <= count; i++) {
            Monster monster = new Monster(this.player.getLevel());
            this.monsters.add(monster);
        }
    }

}
