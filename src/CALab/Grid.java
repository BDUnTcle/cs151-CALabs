package CALab;

import mvc.Model;

import java.util.HashSet;
import java.util.Set;

public abstract class Grid extends Model {
    static private int time = 0;
    protected int dim = 20;
    protected Cell[][] cells;

    public int getDim() {
        return dim;
    }
    public int getTime() {
        return time;
    }
    public Cell getCell(int row, int col) {
        return cells[row][col];
    }
    public abstract Cell makeCell(boolean uniform);

    public Grid() {
        cells = new Cell[dim][dim];
        populate();
    }


    protected void populate() {
        // 1. use makeCell to fill in cells
        for (int row = 0; row < dim; row++){
            for (int col = 0; col < dim; col++){
                cells[row][col] = makeCell(true);
                cells[row][col].setCol(col);
                cells[row][col].setRow(row);
            }
        }
        // 2. use getNeighbors to set the neighbors field of each cell
        for (int row = 0; row < dim; row++){
            for (int col = 0; col < dim; col++){
                cells[row][col].setNeighbors(getNeighbors(cells[row][col], 1));
            }
        }
        repopulate(true);

    }

    // called when Populate button is clicked
    public void repopulate(boolean randomly) {
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                cells[row][col].reset(randomly);
            }
        }
        // notify subscribers
        changed();
    }

        /*
        return the set of all cells that can be reached from the asker in radius steps.
        If radius = 1 this is just the 8 cells touching the asker.
        Tricky part: cells in row/col 0 or dim - 1.
        The asker is not a neighbor of itself.
        */
        public Set<Cell> getNeighbors(Cell asker, int radius) {
            Set<Cell> neighbors = new HashSet<>();
            int row = asker.getRow();
            int col = asker.getCol();
            for (int i = -radius; i <= radius; i ++) {
                for (int j = -radius; j <= radius; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    int neighborRow = (row + i + dim) % dim;
                    int neighborCol = (col + j + dim) % dim;
                    neighbors.add(cells[neighborRow][neighborCol]);
                }
            }
            return neighbors;
        }
    // cell phases:

    public void observe() {
        // Call observe method of each cell
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                cells[row][col].neighbors = getNeighbors(cells[row][col], 1);
                cells[row][col].observe();
            }
        }
        changed();
    }

    public void interact() {
        // Call interact method of each cell
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                cells[row][col].interact();
            }
        }
        changed();
    }

    public void update() {
        // Call update method of each cell
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                cells[row][col].update();
            }
        }
        changed();
    }

    public void updateLoop(int cycles) {
        observe();
        for(int cycle = 0; cycle < cycles; cycle++) {
            interact();
            update();
            observe();
            time++;
        }
    }
}

