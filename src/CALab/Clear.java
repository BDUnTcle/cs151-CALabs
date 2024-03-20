package CALab;

import mvc.Command;
import mvc.Model;

public class Clear extends Command {
    public Clear(Model m)
    {
        super(m);
    }

    @Override
    public void execute() {
        Grid g = (Grid)getModel();
        g.repopulate(false);
        g.observe();
        g.update();
    }
}
