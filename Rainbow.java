import javafx.scene.paint.Color;
import java.util.List;

/**
 * Write a description of class Rainbow here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Rainbow extends Cell
{
    // Constructor for rainbow
    public Rainbow(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
    * This is how the rainbow cell decides if it's alive or not
    */
      public void act() {
        if (diseaseChecks()) {
            int numberOfAliveNeighbours = getNumberOfAliveNeighbours();
            if(numberOfAliveNeighbours % 2==0){
                setColor(Color.GREEN); // sets the colour to green if the num. of neightbours is even 
                if(numberOfAliveNeighbours > 3){ // deletes if the number of neigbours is greater than 3
                     setNextState(false);
                }
                else{
                    setNextState(true); //reinstates if the number is less than 4
                }
            }
            else if(numberOfAliveNeighbours % 2==1){
                 setColor(Color.BLUE); // sets the colour to green if the num. of neightbours is even 
                if(numberOfAliveNeighbours < 4){ // reinsates if the number of neigbours is less then 4
                     setNextState(true);
                }
                else{
                    setNextState(false); //deletes if the number is greater then 3
                }
            }
        }
    }
}
