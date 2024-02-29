import javafx.scene.paint.Color; 
import java.util.Date; 
import java.util.concurrent.TimeUnit;

/**
 * A class representing the shared characteristics of all forms of life
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06
 */

public abstract class Cell {

    private boolean alive;    
    private boolean nextAlive; // The state of the cell in the next iteration
    private Field field;
    private Location location;
    private Color color = Color.WHITE;
    private boolean diseased = false;
    private boolean immune = false;
    private int diseaseTimer = 3;   // stores the number of remaining generations that the cell is diseased for (when diseased is true)
    private int immuneTimer = 5;    // stores the number of remaining generations that the cell is immune for (when immune is true)

    /**
     * Create a new cell at location in field
     * @param field The field currently occupied
     * @param location The location within the field
     */
    public Cell(Field field, Location location, Color col) {
        alive = true;
        nextAlive = false;
        this.field = field;
        setLocation(location);
        setColor(col);
    }

    /**
     * Make this cell act - that is: the cell decides it's status in the
     * next generation.
     */
    abstract public void act();

    /**
     * Check whether the cell is alive or not.
     * @return true if the cell is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }
    
    protected boolean nextAlive() {
        return nextAlive;
    }

    /**
     * Indicate that the cell is no longer alive.
     */
    protected void setDead() {
        alive = false;
    }

    /**
     * Indicate that the cell will be alive or dead in the next generation.
     */
    public void setNextState(boolean value) {
        nextAlive = value;
    }

    /**
     * Changes the state of the cell
     */
    public void updateState() {
        alive = nextAlive;
    }

    /**
     * Changes the color of the cell
     */
    public void setColor(Color col) {
        color = col;
    }

    /**
     * Returns the cell's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Return the cell's location.
     * @return The cell's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Place the cell at the new location in the given field.
     * @param location The cell's location.
     */
    protected void setLocation(Location location) {
        this.location = location;
        field.place(this, location);
    }

    /** 
     * Return the cell's field.
     * @return The cell's field.
     */
    protected Field getField() {
        return field;
    }

    protected void resetColour() {
        setColor(color);
    }
    
    /**
     * Returns the number of alive neighbours that the cell has
     */
    protected int getNumberOfAliveNeighbours() {
        int count = 0;
        Field field = getField();
        for (Location l : field.adjacentLocations(location))
        {
            if (field.getObjectAt(l).isAlive())
            {
                count++;
            }
        }  
        return count;
    }
    
    /**
     * Returns true if the cell is diseased
     */
    protected boolean isDiseased() {
        return diseased;
    }
    
    /**
     * Sets the value of diseased to true
     */
    private void setDiseased() {
        diseased = true;
    }
    
    /**
     * Sets the value of diseased to false
     */
    private void setNotDiseased() {
        diseased = false;
    }
    
    /**
     * Returns true if the cell is immune
     */
    protected boolean isImmune() {
        return immune;
    }
    
    /**
     * Sets the value of immune to true
     */
    protected void setImmune() {
        immune = true;
        immuneTimer = 7;
    }
    
    /**
     * Sets the value of immune to false
     */
    protected void setNotImmune() {
        immune = false;
    }
    
    /**
     * Returns true if the cell has neighbours that are diseased
     */
    private boolean hasDiseasedNeighbours() {
        for (Location neighbour: field.adjacentLocations(location))
        {
            if (field.getObjectAt(neighbour).isDiseased()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Makes the cell diseased for 3 generations
     */
    protected void makeDiseased() {
        setDiseased();
        diseaseTimer = 3;
        setColor(Color.BLACK);
    }
    
    /**
     * Makes the cell act differently to normal when it is diseased
     */
    private void actDiseased() {
        diseaseTimer --;
        if (getNumberOfAliveNeighbours() >= 2) {
            // the cell lives if it has more than one alive neighbour
            setNextState(true);
        }
        else {
            // the cell dies if it has none or one alive neighbour
            setNextState(false);
        }
    }
    
    /**
     * Returns true if the cell is not diseased and can act normally, returns false otherwise and acts diseased
     */
    protected boolean diseaseChecks() {
        if (immuneTimer == 0) {
            // sets the cell to not immune if the timer has run out
            setNotImmune();
        }
        if (isDiseased()) {
            if (diseaseTimer == 0) {
                // makes the cell immune and sets it to not diseased when the diseased timer runs out
                setNotDiseased();
                setImmune();
                return true;
            }
            else {
                actDiseased();
            }
        }
        else if (isImmune()) {
            // decrements the immune timer and the cell acts normally
            immuneTimer --;
            return true;
        }
        else {
            if (hasDiseasedNeighbours() && isAlive()) {
                // alive cells with diseased neighbours will become diseased
                makeDiseased();
            }
            else {
                return true;
            }
        }
        return false;
    }
}
