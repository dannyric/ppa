import javafx.scene.paint.Color; 
import java.util.List;

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

    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }

    
    // Ran a few times and seems to be working fine
    /**
    * This is how the Mycoplasma decides if it's alive or not
    */
    public void act() {
        /*
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        if (isAlive()) {
            if (neighbours.size() > 1)
                setNextState(true);
            }
        */
       
        // Dies if fewer than two of its neighbours are alive
        if (numberOfAliveNeighbours()<2 || numberOfAliveNeighbours()>3)
        {
            setNextState(false);
        }
        // Cell is alive if exactly 3 neighbours are alive
        else if (numberOfAliveNeighbours()==3)
        {
            setNextState(true);
        }
        // Stays in current state if exactly 2 neighbours are alive (or any other situation than those mentioned above)
    }
}
