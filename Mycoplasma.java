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

    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not
     */
    public void act() {
    
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        int numberOfAliveNeighbours = getNumberOfAliveNeighbours();
        // Dies if fewer than two of its neighbours are alive
    
        if (getColor() == Color.BLACK){
            isAlive();
        }
        else if (numberOfAliveNeighbours<2 || numberOfAliveNeighbours>3)
            {
                setNextState(false);
            }
            // Cell is alive if exactly 3 neighbours are alive
        else if (numberOfAliveNeighbours==3)
            {
                setNextState(true);
            }
            // Stays in current state if exactly 2 neighbours are alive (or any other situation than those mentioned above)
        Random random = Randomizer.getRandom();

        if((isAlive() == true) && random.nextDouble() <= DISEASED_PROB){ //checks if the cell is alive and if meets the chance the cell is effect by a disease 
            
          makeDiseased();
        }
    }

}

