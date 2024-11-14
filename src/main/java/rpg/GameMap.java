package rpg;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

class GameMap {
    private Tile[][] map;
    private int width;
    private int height;
    private Player player;
    private Game game;
    private int tileX = 0;
    private int tileY = 0;
    private Shop lastShop;
    private Monster lastMonster;
    private Obstacle lastObstacle;
    // Référence au jeu

    public GameMap(int width, int height, Player player, Game game) {
        this.width = width;
        this.height = height;
        this.map = new Tile[width][height];
        this.player = player;
        this.game = game;
        generateMap();
    }

    private void generateMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = new EmptyTile();
            }
        }

        addMonsters();
        addShops();
        addPlayer();

        Random rand = new Random();
        for (int x = 0; x < this.width + this.height; x++) {
            addWall();
        }

        int numberOfObstacles = rand.nextInt(width) + 1;
        for (int x = 0; x < numberOfObstacles; x++) {
            addObstacle();
        }

        int numberOfShop = rand.nextInt(width / 2) + 1;
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

        map[x][y] = new ShopTile(new Shop(this.player)); // (x+1, y) pour le magasin

    }

    private void addObstacle() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(width);
            y = rand.nextInt(height);
        } while (x == width - 1 && y == height - 1 || x == 0 && y == 0);  // Vérifie que ce n'est pas la dernière case

        map[x][y] = new ObstacleTile(new Obstacle("rocher"));
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
            if (player.getX() == tileX && player.getY() == tileY && lastShop != null) {
                map[tileX][tileY] = new ShopTile(lastShop); // Restaurer le magasin
            } else if (player.getX() == tileX && player.getY() == tileY && lastMonster != null) {
                map[tileX][tileY] = new MonsterTile(lastMonster); // Restaurer le monstre
            } else if (player.getX() == tileX && player.getY() == tileY && lastObstacle != null) {
                map[tileX][tileY] = new ObstacleTile(lastObstacle); // Restaurer le monstre
            } else {
                map[player.getX()][player.getY()] = new EmptyTile(); // Sinon, mettre une case vide
            }


            // Déplacement du joueur
            player.setPosition(newX, newY);
            map[newX][newY] = new PlayerTile(player); // Nouvelle position
        } else if (targetTile instanceof MonsterTile) {
            Monster monster = ((MonsterTile) targetTile).getMonster();
            System.out.println("Vous rencontrez un " + monster.getName() + " !");
            Fight fight = new Fight(this.game, monster);
            if (Objects.equals(fight.fightExecute(monster), "win")) {
                map[player.getX()][player.getY()] = new EmptyTile();
                player.setPosition(newX, newY);
                map[newX][newY] = new PlayerTile(player);
                if (lastMonster != null) {
                    this.lastMonster = null;
                }
            } else if (Objects.equals(fight.fightExecute(monster), "escape")) {
                tileX = newX;
                tileY = newY;
                lastMonster = monster;
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
            tileX = newX;
            tileY = newY;
            lastShop = shop;
            map[player.getX()][player.getY()] = new EmptyTile();
            player.setPosition(newX, newY);
            map[newX][newY] = new PlayerTile(player);
        } else if (targetTile instanceof ObstacleTile) {
            Obstacle obstacle = ((ObstacleTile) targetTile).getObstacle();
            System.out.println("Vous rencontrez un " + obstacle.getName() + " !");
            Fight fight = new Fight(this.game, obstacle);
            if (Objects.equals(fight.fightExecute(obstacle), "win")) {
                map[player.getX()][player.getY()] = new EmptyTile();
                player.setPosition(newX, newY);
                map[newX][newY] = new PlayerTile(player);
                if (lastObstacle != null) {
                    this.lastObstacle = null;
                }
            } else if (Objects.equals(fight.fightExecute(obstacle), "escape")) {
                tileX = newX;
                tileY = newY;
                lastObstacle = obstacle;
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
            Scanner scanner = new Scanner(System.in);
            String input;
            while (true) { // Boucle infinie jusqu'à ce qu'une entrée valide soit donnée
                System.out.print("Voulez-vous continuer ? (oui/non) : ");
                input = scanner.nextLine().toLowerCase();

                switch (input) {
                    case "oui":
                    case "o":
                        this.game.getPlayer().setPosition(0, 0);
                        generateMap();
                        game.startGame();
                        return false;

                    case "non":
                    case "n":
                        return true;

                    default:
                        System.out.println("Entrée invalide. Veuillez ressaisir (oui/non).");
                }
            }
        }
        return false;
    }


}
