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
    protected boolean diseased = false;
    private boolean immune = false;
    private int diseaseTimer = 3;
    private int immuneTimer = 5;
    protected static final double DISEASED_PROB = 0.0001;

    /**
     * Create a new cell at location in field.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
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
    
    private boolean isDiseased() {
        return diseased;
    }
    
    private void setDiseased() {
        diseased = true;
    }
    
    private void setNotDiseased() {
        diseased = false;
    }
    
    private boolean isImmune() {
        return immune;
    }
    
    private void setImmune() {
        immune = true;
        immuneTimer = 5;
    }
    
    private void setNotImmune() {
        immune = false;
    }
    
    private boolean hasDiseasedNeighbours() {
        for (Location neighbour: field.adjacentLocations(location))
        {
            if (field.getObjectAt(neighbour).isDiseased()) {
                return true;
            }
        }
        return false;
    }

    protected void makeDiseased(){ // changes the cell colour to black as well as the surrounding cells if they are alive and the same type of organism
        setDiseased();
        diseaseTimer = 3;
        setColor(Color.BLACK);
        Field field2 = getField();
        for (Location m : field2.adjacentLocations(location))
        {
            if (field.getObjectAt(m).isAlive())
            {
                setColor(Color.BLACK);
            }
        }
    }
    
    private void actDiseased() {
        diseaseTimer --;
        if (getNumberOfAliveNeighbours() >= 2) {
            setNextState(true);
        }
        else {
            setNextState(false);
        }
    }
    
    protected boolean diseaseChecks() {
        // Returns true if the cell is not diseased and can act normally, returns false otherwise
        if (immuneTimer == 0) {
            setNotImmune();
        }
        if (isDiseased()) {
            if (diseaseTimer == 0) {
                setImmune();
                return true;
            }
            else {
            actDiseased();
            }
        }
        else if (isImmune()) {
            immuneTimer --;
            return true;
        }
        else {
            if (hasDiseasedNeighbours() && isAlive()) {
                makeDiseased();
            }
            else {
                return true;
            }
        }
        return false;
    }
}
