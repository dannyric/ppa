import javafx.scene.paint.Color;
import java.util.Random;

public abstract class Symbiosis extends Cell
{
    Location loc;
    double alive_prob = 0.5;
    Random rand = Randomizer.getRandom();
    
    public Symbiosis(Field field, Location location, Color col)
    {
        super(field, location, col);
        loc = location;
    }
    
    protected int getNumberOfSymbioticNeighbours() {
        int count = 0;
        Field field = getField();
        for (Location l : field.adjacentLocations(loc))
        {
            if (field.getObjectAt(l) instanceof Symbiosis)
                {
                    count++;
                }
            }
        return count;
    }
    
    public void act()
    {
        switch (getNumberOfSymbioticNeighbours()) {
            case 0:
                alive_prob = 0.25;
                break;
            case 1:
                alive_prob = 0.5;
                break;
            case 2:
                alive_prob = 0.75;
                break;
            case 3:
                alive_prob = 0.9;
                break;
            default:
                alive_prob = 0.4;
        }
        if (rand.nextDouble() < alive_prob) {
            setNextState(true);
        }
        else {
            setNextState(false);
        }
    }
}
