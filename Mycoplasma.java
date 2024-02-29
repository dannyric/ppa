import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life.  A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06
 */

public class Mycoplasma extends Cell {
    
    private final double DISEASED_PROB = 0.0005; // the probability that a Mycoplasma cell will become diseased
    
    /**
     * Create a new Mycoplasma Cell
     */
    
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not
     */
    public void act() {
        int numberOfAliveNeighbours = getNumberOfAliveNeighbours();
        
        if (isAlive()) {
            if (numberOfAliveNeighbours<2 || numberOfAliveNeighbours>3) {
                // Dies if fewer than two or more than 3 of its neighbours are alive
                setNextState(false);
            }
            else {
                // Lives on if 2 or 3 neighbours are alive and it was previously alive
                setNextState(true);
            }
        }
        else if (numberOfAliveNeighbours==3) {
            // Cell is alive if exactly 3 neighbours are alive
            setNextState(true);
        }
        else {
            setNextState(false);
        }
        
        Random rand = Randomizer.getRandom();
        if(isAlive() && rand.nextDouble()<=DISEASED_PROB){
            // If the cell is alive, it has a 0.05% chance of becoming diseased
            makeDiseased();
        }
    }
}
