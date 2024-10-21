package rpg;

public class Obstacle extends Breakable{

    public static final double LIFE = 50;

    public Obstacle(String name) {
        super(name,LIFE);
    }

    @Override
    public void attack(Player player) {

    }

    @Override
    public void passTurn() {

    }
}
