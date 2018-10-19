package task2;

import task1.Tuple;

import java.util.*;

public class ProgramGraph {

    private int numberOfProcesses;
    private HashSet<Location> loc;
    private HashSet<String> act;

    // Location + (Guard + Act) -> location
    private HashSet<ThreeTuple<Location, Tuple<String, String>, Location>> arrow;
    private HashSet<Location> loc0;

    public ProgramGraph() {
        this.loc = new HashSet<>();
        this.act = new HashSet<>();
        this.act.add("goWait");
        this.act.add("goCrit");
        this.act.add("goNonCrit");
        this.arrow = new HashSet<>();
        this.loc0 = new HashSet<>();
    }

    public void generate(int numberOfProcesses) {
        this.numberOfProcesses = numberOfProcesses;

        LinkedList<Tuple<Location, Location>> queue = new LinkedList<>();

        createLocations(queue, numberOfProcesses);

        Stack<Location> toCreate = new Stack<>();
        toCreate.addAll(loc);

        createArrow(toCreate, numberOfProcesses);
    }

    private void createLocations(LinkedList<Tuple<Location, Location>> queue, int numberOfProcesses) {
        Tuple<Location, Location> current;
        Location curLoc;

        if (queue.size() == 0) {
            if (loc.size() == 0)
            {
                curLoc = new Location(numberOfProcesses);
                loc0.add(curLoc);
                loc.add(curLoc);

                for (int i = 0; i < numberOfProcesses; ++i)
                {
                    Location next = new Location(curLoc.data, (LinkedList<Process>) curLoc.queue.clone());

                    next.updateProcessState(i);
                    queue.add(new Tuple<>(curLoc, next));
                }
            }
            else
            {
                return;
            }
        }
        else
        {
            current = queue.pop();
            curLoc = current.second;
            loc.add(curLoc);

            for (int i = 0; i < numberOfProcesses; ++i)
            {
                Location next = new Location(curLoc.data, (LinkedList<Process>) curLoc.queue.clone());

                next.updateProcessState(i);

                if (!loc.contains(next))
                {
                    queue.addLast(new Tuple<>(curLoc, next));
                }
            }
        }

        createLocations(queue, numberOfProcesses);
    }
    
    private void createArrow(Stack<Location> toDo, int numberOfProcesses)
    {
        if (toDo.size() == 0)
        {
            return;
        }

        Location curLoc = toDo.pop();

        for (int i = 0; i < numberOfProcesses; ++i)
        {
            Location clonedCurrent = new Location(curLoc.data, (LinkedList<Process>) curLoc.queue.clone());

            ProgramState state = clonedCurrent.updateProcessState(i);

            if (!state.equals(curLoc.data.get(i).programState))
            {
                ThreeTuple<Location, Tuple<String, String>, Location> nextArrow = new ThreeTuple<>(curLoc, clonedCurrent);

                switch (state) {
                    case NON_CRIT: {
                        nextArrow.second = new Tuple<>("Crit-NonCrit", "goNonCrit") ;

                        break;
                    }
                    case WAIT: {
                        nextArrow.second = new Tuple<>("NonCrit-Wait", "goWait");

                        break;
                    }
                    case CRIT: {
                        nextArrow.second = new Tuple<>("Wait-Crit", "goCrit");

                        break;
                    }
                }

                if (!arrow.contains(nextArrow))
                {
                    arrow.add(nextArrow);
                }
            }

        }
        
        createArrow(toDo, numberOfProcesses);
    }

    public static ProgramGraph getInstance(int numProcesses) {
        ProgramGraph pg = new ProgramGraph();

        pg.generate(numProcesses);

        return pg;
    }

    public HashSet<Location> getLocations() {
        return loc;
    }

    public HashSet<Location> getLoc0() {
        return loc0;
    }

    public HashSet<String> getActions() {
        return act;
    }

    public HashSet<ThreeTuple<Location, Tuple<String, String>, Location>> getArrow() {
        return arrow;
    }

    public int getNumberOfProcesses() {
        return numberOfProcesses;
    }
}
