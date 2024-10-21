package rpg;

class Tile {
    // Classe de base pour les tuiles
}

class MonsterTile extends Tile {
    private Monster monster;

    public MonsterTile(Monster monster) {
        this.monster = monster;
    }

    public Monster getMonster() {
        return monster;
    }

    @Override
    public String toString() {
        return "M"; // Représentation du monstre
    }
}

class PlayerTile extends Tile {
    private Player player;

    public PlayerTile(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "P"; // Représentation du joueur
    }

    public Player getPlayer() {
        return player;
    }
}
class EndTile extends Tile {
    @Override
    public String toString() {
        return "F";
    }
}

class ShopTile extends Tile {
    private Shop shop;

    public ShopTile(Shop shop) {
        this.shop = shop;
    }

    public Shop getShop() {
        return shop;
    }

    @Override
    public String toString() {
        return "S"; // Représentation du magasin
    }
}

class obstacleTile extends Tile {
    private Obstacle obstacle;

    public obstacleTile(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public Obstacle getObstacle() {
        return this.obstacle;
    }

    @Override
    public String toString() {
        return "O"; // Représentation du magasin
    }
}


class wallTile extends Tile {
    @Override
    public String toString() {
        return "#"; // Représentation d'un mur
    }
}

class EmptyTile extends Tile {
    @Override
    public String toString() {
        return "."; // Représentation d'une case vide
    }
}
