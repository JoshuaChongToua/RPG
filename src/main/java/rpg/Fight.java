package rpg;

import rpg.Classes.Mage;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Fight {
    private Game game;
    private Breakable breakable;
    private int nturn;
    private boolean escape = false;

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
            if (Objects.equals(this.game.getPlayer().getClasse().getName(), "Mage")) {
                Mage mage = (Mage) this.game.getPlayer().getClasse();
                System.out.println("Mana : " + mage.getMana());
                System.out.println("1. Attaquer   2. Sorts   3. Inventaire   4. Passez le tour   5. Fuir");

            } else {
                System.out.println("Stamina : " + this.game.getPlayer().getStamina());
                System.out.println("1. Attaquer   2. Attaque Spéciale   3. Inventaire   4. Passez le tour   5. Fuir");
            }
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
                    if (breakable instanceof Monster monster) {
                        if (!Objects.equals(this.game.getPlayer().getClasse().getName(), "Mage")) {
                            if (this.game.getPlayer().specialAttack(monster)) {
                                System.out.println("Vous utilisé votre attaque spéciale sur la cible");
                                validAction = true;
                            }
                        } else {
                            Mage mage = (Mage) this.game.getPlayer().getClasse();
                            System.out.println("1. Bruler   2. Geler   3. Paraliser   5. Retour");
                            String choiceSpells = sc.nextLine();
                            switch (choiceSpells) {
                                case "1":
                                    if (mage.burn(monster)) {
                                        validAction = true;
                                    }
                                    break;

                                case "2":
                                    if (mage.freeze(monster)) {
                                        validAction = true;
                                    }
                                    break;

                                case "3":
                                    if (mage.paralyse(monster)) {
                                        validAction = true;
                                    }
                                    break;

//                                case "4":
//                                    mage.poison(monster);
//                                    validAction = true;
//                                    break;

                                case "5":
                                    break;

                                default:
                                    System.out.println("Choix invalide. Essayez de nouveau.");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("La cible n'est pas un monstre");
                    }

                    break;

                case "3":
                    System.out.println("Affichage de l'inventaire...");
                    this.game.getPlayer().showInventaire();
                    break; // Pas de validAction = true ici pour permettre de choisir une autre action

                case "4":
                    this.game.getPlayer().passTurn();
                    validAction = true;
                    break;

                case "5":
                    if (attemptEscape(breakable)) {
                        this.escape = true;
                        System.out.println("Vous avez réussi à fuir le combat !");
                        return;
                    } else {
                        System.out.println("Echec de la tentative de fuite");
                    }
                    validAction = true;
                    break;
                case "6":
                    if (breakable instanceof Monster monster) {
                        monster.showStats();
                    }
                default:
                    System.out.println("Choix invalide. Essayez de nouveau.");
                    break;
            }

            // Affiche les points de vie du joueur et de la cible
            System.out.println("Vos pv : " + Math.round(this.game.getPlayer().getHealth()*100)/100);
            System.out.println("Pv Monstre : " + Math.round(this.breakable.getHealth() * 100)/100);
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
        System.out.println("Vos pv : " + Math.round(this.game.getPlayer().getHealth()*100)/100);
        System.out.println("Pv Monstre : " + Math.round(this.breakable.getHealth() * 100)/100);
        System.out.println("=============================");
    }

    public String fightExecute(Breakable breakable) {
        if (breakable instanceof Monster monster) {
            System.out.println("Début du combat : " + this.game.getPlayer().getName() + " vs " + monster.getName());
            System.out.println("Vos pv : " + Math.round(this.game.getPlayer().getHealth()*100)/100);
            System.out.println("Pv Monstre : " + Math.round(monster.getHealth() * 100)/100);

            // La vitesse indique qui commence
            var tourJoueur = this.game.getPlayer().getSpeed() >= monster.getSpeed();
            int nturn = 1;
            int valideTurn = 0;
            System.out.println("Tour : " + nturn);

            // Tant que le joueur et le monstre sont vivants
            while (this.game.getPlayer().getHealth() > 0 && monster.getHealth() > 0 && !this.escape) {
                this.resetState();
                if (tourJoueur) {
                    this.checkStatePlayer(monster);
                    valideTurn++;
                } else {
                    this.checkStateMonster();
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
            System.out.println("Vos pv : " + Math.round(this.game.getPlayer().getHealth()*100)/100);
            System.out.println("Pv Obstacle : " + Math.round(obstacle.getHealth()*100)/100);

            int nturn = 1;
            System.out.println("Tour : " + nturn);

            while (this.game.getPlayer().getHealth() > 0 && obstacle.getHealth() > 0 && !this.escape) {
                playerTurn(obstacle
                );

                // Empêche les points de vie d'être négatifs
                this.game.getPlayer().setHealth(Math.max(0, this.game.getPlayer().getHealth()));
                obstacle.setHealth(Math.max(0, obstacle.getHealth()));

                // Incrémentation de nturn après chaque tour complet (joueur + breakable)
                nturn++;
                System.out.println("Tour : " + nturn);
            }
        }
        // Fin du combat, affichage du résultat
        if (this.game.getPlayer().getHealth() <= 0) {
            System.out.println("Game over ! " + this.game.getPlayer().getName() + " a été vaincu !");
            return "loose";
        } else if (this.escape) {
            return "escape";
        } else {
            System.out.println("BRAVO ! " + breakable.getName() + " a été vaincu !");
            Random random = new Random();
            double xp;
            if (breakable instanceof Monster monster) {
                xp = 100 + (double) (random.nextInt(201) * monster.getLevel()) / 3;
            } else {
                xp = 100 + random.nextInt(201);
            }
            int money = 50 + random.nextInt(51);
            this.game.getPlayer().setMoney(this.game.getPlayer().getMoney() + money);
            System.out.println("Vous recevez : " + money + " pièces");
            this.game.getPlayer().xpManager(xp);
            return "win";
        }
    }

    private boolean attemptEscape(Breakable breakable) {
        Random rand = new Random();
        int chance;

        if (breakable instanceof Monster monster) {
            // Détermine la probabilité en fonction de la vitesse du joueur
            chance = (this.game.getPlayer().getSpeed() > monster.getSpeed()) ? 2 : 4;
        } else {
            // Si ce n'est pas un monstre, utilise une probabilité fixe
            chance = 3;
        }

        // Vérifie la probabilité de fuite
        return rand.nextInt(chance) == 1;
    }

    private void resetState() {
        Random rand = new Random();
        if (!Objects.equals(this.game.getPlayer().getState(), "")) {
            String state = this.game.getPlayer().getState();
            int luck = rand.nextInt(3);
            if (luck == 1) {
                System.out.println("Vous n'êtes plus " + state);
                this.game.getPlayer().setState("");
            }
        }

        if (this.breakable instanceof Monster monster) {
            if (!Objects.equals(monster.getState(), "")) {
                String state = monster.getState();
                int luck = rand.nextInt(3);
                if (luck == 1) {
                    System.out.println("L'ennemi n'est plus " + state);
                    monster.setState("");
                }
            }
        }
    }

    private void checkStatePlayer(Breakable breakable) {
        if (this.game.getPlayer().getState().equals("burn")) {
            this.game.getPlayer().setHealth(-5);
        } else if (this.game.getPlayer().getState().equals("freeze")) {
            this.game.getPlayer().passTurn();
        } else if (this.game.getPlayer().getState().equals("paralyse")) {
            this.game.getPlayer().setSpeed(this.game.getPlayer().getSpeed() / 2);
        } else {
            this.playerTurn(breakable);
        }
    }

    //        if (this.game.getPlayer().getState().equals("poison")) {
//            this.game.getPlayer().setHealth(-poison);
//        }
    private void checkStateMonster() {
        if (breakable instanceof Monster monster) {
            if (monster.getState().equals("burn")) {
                System.out.println("Pv ennemie : " + monster.getHealth() + " - 5");
                monster.setHealth(-5);
                this.monsterTurn();
            }
            else if (monster.getState().equals("freeze")) {
                System.out.println("Ennemi gelé");
                monster.passTurn();
            }
            else if (monster.getState().equals("paralyse")) {
                monster.setSpeed(this.game.getPlayer().getSpeed() / 2);
                this.monsterTurn();
            }
            else {
                this.monsterTurn();
            }
        }


    }
}
