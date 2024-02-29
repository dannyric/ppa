import javafx.scene.paint.Color;
import java.util.Random;

/**
 * The cell behaves in a non-deterministic manner and has a mutualistic relationship with time cells where it can be granted immunity from disease
 */
public class Nondeterministic extends Cell
{
    Location loc; // stores the location of the cell
    
    /**
     * Constructor for Nondeterministic cells
     */
    public Nondeterministic(Field field, Location location, Color col)
    {
        super(field, location, col);
        loc = location;
    }
    
    /**
     * This returns the number of neighbours that are time cells
     */
    private int getNumberOfTCNeighbours() {
        int count = 0;
        Field field = getField();
        for (Location l : field.adjacentLocations(loc))
        {
           if (field.getObjectAt(l) instanceof TimeCell)
           {
               // if a neighbour is a nondeterministic cell, add one to the count
               count++;
           }
        }
        return count;
    }
    
    /**
     * This is how the nondeterministic cell decides if it's alive or not
     */
    public void act()
    {
        boolean immunityGranted = false;
        Random rand = new Random();
            
        if (getNumberOfTCNeighbours()>2 && !isDiseased() && !isImmune() && rand.nextDouble()<=0.5) {
            // if the cell is not already infected or immune, there is a 50% chance that it will be granted immunity for one generation only
            setImmune();
            immunityGranted = true;
        }
        if (diseaseChecks()) {      // the cell acts normally if it is not affected by disease
            resetColour();
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
                // The cell's state will be reversed
                if (isAlive()) {
                    setNextState(false);
                }
                else {
                    setNextState(true);
                }
            }
        }
        if (immunityGranted) {
            // cancels the immunity if it was granted at the start
            setNotImmune();
        }
    }
}
