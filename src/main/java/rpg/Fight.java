package rpg;

import java.util.Random;
import java.util.Scanner;

public class Fight {
    private Game game;
    private Breakable breakable;
    private int nturn;

    public Fight(Game game, Monster monster) {
        this.game = game;
        this.breakable = monster;

    }

    public Fight(Game game, Obstacle obstacle) {
        this.game = game;
        this.breakable = obstacle;
    }

    public void playerTurn(Breakable breakable) {
        System.out.println("=============================");
        System.out.println("C'est à vous de jouer ");
        Scanner sc = new Scanner(System.in);

        boolean validAction = false;
        while (!validAction) {
            System.out.println("1. Attaquer   2. Inventaire   3. Passez le tour   4. Fuir");
            String choice = sc.nextLine();
            System.out.println("=============================");

            switch (choice) {
                case "1":
                    System.out.println("Vous attaquez la cible");
                    // Vérifiez si la cible est un monstre ou un obstacle
                    if (breakable instanceof Monster monster) {
                        this.game.getPlayer().attack(monster);  // Attaque le monstre
                    } else if (breakable instanceof Obstacle obstacle) {
                        this.game.getPlayer().attack(obstacle);  // Attaque l'obstacle
                    }
                    validAction = true;
                    break;

                case "2":
                    System.out.println("Affichage de l'inventaire...");
                    this.game.getPlayer().showInventaire();
                    break; // Pas de validAction = true ici pour permettre de choisir une autre action

                case "3":
                    this.game.getPlayer().passTurn();
                    validAction = true;
                    break;

                case "4":
                    System.out.println("Vous avez choisi de fuir le combat !");
                    validAction = true;
                    break;

                default:
                    System.out.println("Choix invalide. Essayez de nouveau.");
                    break;
            }

            // Affiche les points de vie du joueur et de la cible
            System.out.println("Vos pv : " + this.game.getPlayer().getHealth());
            System.out.println("Pv de la cible : " + breakable.getHealth());
            System.out.println("=============================");
        }
    }



    public void monsterTurn() {
        Random random = new Random();
        int nombre = random.nextInt(100) + 1;  // Tire un nombre aléatoire entre 1 et 100
        System.out.println("Tour de " + this.breakable.getName()); // affiche le tour du monstre
        System.out.println("=============================");
        if (nombre <= 70) { // Si le nombre tiré est inférieur à 70 le monstre attaque
            System.out.println("L'adversaire vous attaque");
            this.breakable.attack(this.game.getPlayer());
        } else { // Si le nombre tiré est superieur à 70 le monstre passe son tour
            this.breakable.passTurn();
        }
        System.out.println("Vos pv : " + this.game.getPlayer().getHealth());
        System.out.println("Pv Monstre : " + this.breakable.getHealth());
        System.out.println("=============================");
    }

    public boolean fightExecute(Breakable breakable) {
        if (breakable instanceof Monster monster) {
            System.out.println("Début du combat : " + this.game.getPlayer().getName() + " vs " + monster.getName());
            System.out.println("Vos pv : " + this.game.getPlayer().getHealth());
            System.out.println("Pv Monstre : " + monster.getHealth());

            // La vitesse indique qui commence
            var tourJoueur = this.game.getPlayer().getSpeed() >= monster.getSpeed();
            int nturn = 1;
            int valideTurn = 0;
            System.out.println("Tour : " + nturn);

            // Tant que le joueur et le monstre sont vivants
            while (this.game.getPlayer().getHealth() > 0 && monster.getHealth() > 0) {

                if (tourJoueur) {
                    playerTurn(monster);
                    valideTurn++;
                } else {
                    monsterTurn();
                    valideTurn++;
                }

                tourJoueur = !tourJoueur; // Alternance des tours entre le joueur et le monstre

                // Vérifie si les deux ont joué pour ce tour
                if (valideTurn == 2) { // Si on retourne au joueur après le monstre, le tour est complet
                    // Empêche les points de vie d'être négatifs
                    this.game.getPlayer().setHealth(Math.max(0, this.game.getPlayer().getHealth()));
                    monster.setHealth(Math.max(0, monster.getHealth()));

                    // Incrémentation de nturn après chaque tour complet
                    nturn++;
                    System.out.println("Tour : " + nturn);
                    valideTurn = 0;
                }
            }

        } else if (breakable instanceof Obstacle obstacle) {
            System.out.println("Début du combat : " + this.game.getPlayer().getName() + " vs " + obstacle.getName());
            System.out.println("Vos pv : " + this.game.getPlayer().getHealth());
            System.out.println("Pv Obstacle : " + obstacle.getHealth());

            int nturn = 1;
            System.out.println("Tour : " + nturn);

            // Tant que le joueur et l'obstacle sont "vivants" (ou cassables)
            while (this.game.getPlayer().getHealth() > 0 && obstacle.getHealth() > 0) {
                playerTurn(obstacle);

                // Empêche les points de vie d'être négatifs
                this.game.getPlayer().setHealth(Math.max(0, this.game.getPlayer().getHealth()));
                obstacle.setHealth(Math.max(0, obstacle.getHealth()));

                // Incrémentation de nturn après chaque tour complet
                nturn++;
                System.out.println("Tour : " + nturn);
            }
        }

        // Fin du combat, affichage du résultat
        if (this.game.getPlayer().getHealth() <= 0) {
            System.out.println("Game over ! " + this.game.getPlayer().getName() + " a été vaincu !");
            return false;
        } else {
            System.out.println("BRAVO ! " + breakable.getName() + " a été vaincu !");
            return true;
        }
    }

}
