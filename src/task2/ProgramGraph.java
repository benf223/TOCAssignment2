package task2;

import task1.Tuple;

import java.util.*;

public class ProgramGraph {

    private HashSet<Location> loc;
    private HashSet<String> act;
    private HashMap<Tuple<Location, Tuple<Boolean, String>>, Location> arrow;
    private HashSet<Location> loc0;

    public ProgramGraph(HashSet<Location> loc, HashSet<String> act, HashMap<Tuple<Location, Tuple<Boolean, String>>, Location> arrow, HashSet<Location> loc0) {
        this.loc = loc;
        this.act = act;
        this.arrow = arrow;
        this.loc0 = loc0;
    }

    public void generate(int numberOfProcesses)
    {
        LinkedList<Location> queue = new LinkedList<>();
        queue.add(new Location(numberOfProcesses));

        recur(queue);

    }

    private void recur(LinkedList<Location> queue, int numberofProcesses)
    {
        if (queue.size() == 1)
        {
            if (loc.contains(queue.getFirst()))
            {
                // Check that arrow has all its transitions for the state in the queue


                return;
            }
        }

        Location curLoc = queue.getFirst();
        curLoc.getTriple(numberofProcesses);

        for (int i = 0; i < numberofProcesses; ++i)
        {
            Location next = new Location(numberofProcesses);
            next.setProgramState(i);

            if(!loc.contains(next)){

            }
        }



    }
}
