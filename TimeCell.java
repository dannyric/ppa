import javafx.scene.paint.Color;
import java.util.Random;

public class TimeCell extends Cell {

    /**
     * Create a new time cell.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public TimeCell(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
    * This is how the time cell decides if it's alive or not
    */
    public void act() {
       if (diseaseChecks()) {
           int numberOfAliveNeighbours = getNumberOfAliveNeighbours();
           int generation = Simulator.getGeneration();
           Random rand = new Random();
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
    }
}
