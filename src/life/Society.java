package life;

import CALab.Grid;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Society extends Grid {
    private int percentAlive = 50;
    public static Set<Integer> rebirth = new HashSet<Integer>();
    public static Set<Integer> death = new HashSet<Integer>();

    static {
        rebirth.add(3);
        death.add(0);
        death.add(1);
        death.add(4);
        death.add(5);
        death.add(6);
        death.add(7);
        death.add(8);
    }


    @Override
    public  Agent makeCell(boolean uniform)
    {
        return new Agent(0,0);
    }
    @Override
    public void repopulate(boolean randomly) {
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {

                if (randomly && new Random().nextDouble() * 100 < percentAlive) {
                    cells[row][col].setStatus(1);
                } else {
                    cells[row][col].setStatus(0);
                }
            }
        }
        // notify subscribers
        changed();
    }
}
