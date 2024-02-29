import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Write a description of class Nondeterministic here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Nondeterministic extends Cell
{
    
    /**
     * Constructor for objects of class Nondeterministic
     */
    public Nondeterministic(Field field, Location location, Color col)
    {
        super(field, location, col);
    }
    
    public void act()
    {
        Random rand = new Random();
        double randomDouble = rand.nextDouble();
        if (randomDouble < 0.2){
            // The cell will be alive if it has more than two alive neighbours
            if (getNumberOfAliveNeighbours() > 2){
                setNextState(true);
            }
            else{
                setNextState(false);
            }
        }
        else if (randomDouble < 0.5){
            // 50/50 chance of the cell being alive or dead
            if (rand.nextDouble() < 0.5){
                setNextState(true);
            }
            else{
                setNextState(false);
            }
        }
        else if (randomDouble < 0.7){
            // The cell will be alive if the generation number is even, and dead if it is odd
            if (Simulator.getGeneration() % 2 == 0){
                setNextState(true);
            }
            else{
                setNextState(false);
            }
        }
        else{
            // The cell will live on
            setNextState(true);
        }
    }
}
