package CALab;

import mvc.Publisher;

import java.io.Serializable;
import java.util.*;
import java.awt.Color;


public abstract class Cell extends Publisher implements Serializable {

    protected int row = 0, col = 0;
    protected Set<Cell> neighbors = new HashSet<Cell>();
    protected Grid myGrid = null;
    protected Cell partner = null;
    public Cell(int r,int c)
    {
        super();
        row = r;
        col = c;
    }
    public void setRow(int r){
        row = r;
    }
    public void setCol(int c){
        col = c;
    }
    public int getRow() { return row;}

    public int getCol() { return col;}

    public void setNeighbors(Set<Cell> neighbors) { this.neighbors = neighbors;}

    // choose a random neighbor as a partner
    public void choosePartner() {
        if (partner == null && neighbors != null) {
            // Set partner to null
            partner = null;

            // Convert neighbors set to a local array
            Cell[] neighborsArray = neighbors.toArray(new Cell[0]);

            // Shuffle the array to randomize the order
            List<Cell> shuffledNeighbors = Arrays.asList(neighborsArray);
            Collections.shuffle(shuffledNeighbors);

            // Starting at a random position in the array search for a neighbor without a partner
            for (Cell neighbor : shuffledNeighbors) {
                if (neighbor.partner == null) {
                    // Make the first such neighbor (if any) the partner
                    partner = neighbor;
                    neighbor.partner = this;
                    break;
                }
            }
        }
    }


    public void unpartner() {
        if (partner != null) {
            if (partner.partner != null) {
                partner.partner = null;
            }
            partner = null;
        }
    }
    public abstract void setStatus(int state);
    // observer neighbors' states
    public abstract void observe();
    // interact with a random neighbor
    public abstract void interact();
    // update my state
    public abstract void update();
    // set status to status + 1 mod whatever
    public abstract void nextState();
    // set status to a random or initial value
    public abstract void reset(boolean randomly);

    public abstract int getStatus();

    public abstract Color getColor();

    public abstract int getAmbience();

    






}

