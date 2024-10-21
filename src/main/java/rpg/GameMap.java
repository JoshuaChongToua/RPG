package rpg;

import java.util.ArrayList;
import java.util.Random;

class GameMap {
    private Tile[][] map;  // Matrice de tuiles
    private int width;     // Largeur de la carte
    private int height;    // Hauteur de la carte
    private Player player; // Joueur
    private Game game;
    private int shopX = 0;
    private int shopY = 0;
    private Shop lastShop;
    // Référence au jeu

    public GameMap(int width, int height, Player player, Game game) {
        this.width = width;
        this.height = height;
        this.map = new Tile[width][height]; // La matrice est déclarée avec width puis height
        this.player = player;
        this.game = game;
        generateMap(); // Générer la carte après l'initialisation
    }

    private void generateMap() {
        // Exemple simple de génération de carte
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Remplissez avec des tuiles vides
                map[x][y] = new EmptyTile();
            }
        }

        // Ajouter des monstres à des positions spécifiques
        addMonsters();
        // Ajouter des magasins à des positions spécifiques
        addShops();
        // Ajouter le joueur à sa position initiale
        addPlayer();

        Random rand = new Random();
        for (int x = 0; x < this.width + this.height; x++) {
            addWall();
        }

        int numberOfObstacles = rand.nextInt(width) + 1;
        for (int x = 0; x < numberOfObstacles; x++) {
            addObstacle();
        }

        int numberOfShop = rand.nextInt(width/2) + 1;
        for (int x = 0; x < numberOfShop; x++) {
            addShops();
        }

        addEnd();
    }

    private void addMonsters() {

        ArrayList<Monster> monsters = this.game.getMonsters();
        Random rand = new Random();
        int x, y;
        for (Monster monster : monsters) {
            do {
                x = rand.nextInt(width);
                y = rand.nextInt(height);
            } while (x == width - 1 && y == height - 1 || x == 0 && y == 0);

            map[x][y] = new MonsterTile(monster);
        }

    }

    private void addShops() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(width);
            y = rand.nextInt(height);
        } while (x == width - 1 && y == height - 1 || x == 0 && y == 0);  // Vérifie que ce n'est pas la dernière case

        map[x][y] = new ShopTile(new Shop()); // (x+1, y) pour le magasin

    }

    private void addObstacle() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(width);
            y = rand.nextInt(height);
        } while (x == width - 1 && y == height - 1 || x == 0 && y == 0);  // Vérifie que ce n'est pas la dernière case

        map[x][y] = new obstacleTile(new Obstacle("rocher"));
    }


    private void addWall() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(width);
            y = rand.nextInt(height);
        } while (x == width - 1 && y == height - 1 || x == 0 && y == 0);

        map[x][y] = new wallTile();
    }

    private void addPlayer() {
        map[player.getX()][player.getY()] = new PlayerTile(player); // (x, y) pour le joueur
    }

    private void addEnd() {
        map[width - 1][height - 1] = new EndTile();
    }

    public void displayMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(map[x][y].toString() + " "); // Afficher avec (x, y)
            }
            System.out.println();
        }
    }

    public Tile getTile(int x, int y) {
        return map[x][y]; // Retourne la tuile à la position (x, y)
    }

    public boolean movePlayer(String direction) {
        int newX = player.getX();
        int newY = player.getY();

        switch (direction) {
            case "z": // Up
                newY = (newY > 0) ? newY - 1 : newY;
                break;
            case "s": // Down
                newY = (newY < height - 1) ? newY + 1 : newY;
                break;
            case "q": // Left
                newX = (newX > 0) ? newX - 1 : newX;
                break;
            case "d": // Right
                newX = (newX < width - 1) ? newX + 1 : newX;
                break;
            case "i":
                this.player.showInventaire();
                break;
            default:
                System.out.println("Direction invalide. Utilisez z/q/s/d.");
                break;
        }

        Tile targetTile = getTile(newX, newY);

        if (targetTile instanceof EmptyTile) {
            // Restaurer le magasin si le joueur quitte cette case
            if (player.getX() == shopX && player.getY() == shopY && lastShop != null) {
                //lastShop.setPosition(shopX, shopY);
                map[player.getX()][player.getY()] = new ShopTile(lastShop);
                System.out.println("lalalalala");// Restaurer le magasin à son ancienne position
            } else {
                map[player.getX()][player.getY()] = new EmptyTile();
            }

            // Déplacement du joueur
            player.setPosition(newX, newY);
            map[newX][newY] = new PlayerTile(player); // Nouvelle position
        } else if (targetTile instanceof MonsterTile) {
            Monster monster = ((MonsterTile) targetTile).getMonster();
            System.out.println("Vous rencontrez un " + monster.getName() + " !");
            Fight fight = new Fight(this.game, monster);
            if (fight.fightExecute(monster)) {
                map[player.getX()][player.getY()] = new EmptyTile();
                player.setPosition(newX, newY);
                map[newX][newY] = new PlayerTile(player);
            } else {
                System.out.println("Perdu !!!!");
                player.setPosition(0, 0);
                this.game.resetHealth();
                generateMap();
            }
        } else if (targetTile instanceof ShopTile) {
            Shop shop = ((ShopTile) targetTile).getShop();
            System.out.println("Bienvenue au magasin !");
            this.game.goInShop();
            shopX = newX;
            shopY = newY;
            lastShop = shop;

            map[player.getX()][player.getY()] = new EmptyTile();
            player.setPosition(newX, newY);
            map[newX][newY] = new PlayerTile(player);
        } else if (targetTile instanceof obstacleTile) {
            Obstacle obstacle = ((obstacleTile) targetTile).getObstacle();
            System.out.println("Vous rencontrez un " + obstacle.getName() + " !");
            Fight fight = new Fight(this.game, obstacle);
            if (fight.fightExecute(obstacle)) {
                map[player.getX()][player.getY()] = new EmptyTile();
                player.setPosition(newX, newY);
                map[newX][newY] = new PlayerTile(player);
            } else {
                System.out.println("Perdu !!!!");
                player.setPosition(0, 0);
                this.game.resetHealth();
                generateMap();
            }
        } else if (targetTile instanceof EndTile) {
            System.out.println("Vous êtes sorti du donjon FELICITATION!!!!!!!!");
            map[player.getX()][player.getY()] = new EmptyTile();
            player.setPosition(newX, newY);
            map[newX][newY] = new PlayerTile(player);
            return true;
        }
        return false;
    }

}
