import javafx.scene.paint.Color;
import java.util.Random;

/**
 * This cell exhibits different behaviours as time progresses and has a mutualistic relationship with nondeterministic cells where it can be granted immunity from disease
 */
public class TimeCell extends Cell {
    
    Location loc; // stores the location of the cell
    
    /**
     * Create a new time cell.
     * @param field: The field currently occupied
     * @param location: The location within the field
     * @param col: The colour of the cell
     */
    public TimeCell(Field field, Location location, Color col) {
        super(field, location, col);
        loc = location;
    }
    
    /**
     * This returns the number of neighbours that are Nondeterministic cells
     */
    private int getNumberOfNDNeighbours() {
        int count = 0;
        Field field = getField();
        for (Location l : field.adjacentLocations(loc))
        {
            if (field.getObjectAt(l) instanceof Nondeterministic)
            {
                // if a neighbour is a nondeterministic cell, add one to the count
                count++;
            }
        }
        return count;
    }

    /**
     * This is how the time cell decides if it's alive or not
     */
    public void act() {
       Random rand = new Random();
       boolean immunityGranted = false;
       
       if (getNumberOfNDNeighbours()>2 && !isDiseased() && !isImmune() && rand.nextDouble()<=0.5) {
           // if the cell is not already infected or immune, there is a 50% chance that it will be granted immunity for one generation only
           setImmune();
           immunityGranted = true;
       }
       if (diseaseChecks()) {       // the cell acts normally if it is not affected by disease
           resetColour();
           int numberOfAliveNeighbours = getNumberOfAliveNeighbours();
           int generation = Simulator.getGeneration();
           boolean changed = false;
           
           // Generation 0-10: alive if 2+ alive neighbours
           if (generation <= 10 && numberOfAliveNeighbours > 1){
               setNextState(true);
               changed = true;
           }
           // Generation 10-25: alive if 3+ alive neighbours
           else if (generation <= 25 && numberOfAliveNeighbours > 2){
               setNextState(true);
               changed = true;
           }
           // Generation 25-50: alive if 4+ alive neighbours
           else if (generation <= 50 && numberOfAliveNeighbours > 3){
               setNextState(true);
               changed = true;
           }
           // Generation 50+: alive if 5+ alive neighbours or 25% chance a dead cell will become alive again
           else if (generation >= 50){
               if (numberOfAliveNeighbours > 4 || rand.nextDouble() > 0.25){
                   setNextState(true);
                   changed = true;
               }
           }
           // If none of the above conditions are met, the cell will be dead in the next generation
           if (!changed) {
               setNextState(false);
           }
       }
       if (immunityGranted) {
           // cancels the immunity if it was granted at the start
           setNotImmune();
       }
    }
}
