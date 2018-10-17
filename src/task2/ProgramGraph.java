package task2;

import task1.PropositionalStatement;
import task1.Tuple;

import java.util.*;

public class ProgramGraph {

    private HashSet<Location> loc;
    private HashSet<String> act;
    // Location + (Guard + Act) -> location
    private HashMap<Tuple<Location, Tuple<String, String>>, Location> arrow;
    private HashSet<Location> loc0;

    public ProgramGraph() {
        this.loc = new HashSet<>();
        this.act = new HashSet<>();
        this.act.add("goWait");
        this.act.add("goCrit");
        this.act.add("goNonCrit");
        this.arrow = new HashMap<>();
        this.loc0 = new HashSet<>();
    }

    public void generate(int numberOfProcesses) {
        LinkedList<Location> queue = new LinkedList<>();

        Location l = new Location(numberOfProcesses);
        loc0.add(l);
        loc.add(l);
        queue.add(loc0.iterator().next());

        recur(queue, numberOfProcesses);

        System.out.println("Done");
    }

    private void recur(LinkedList<Location> queue, int numberofProcesses) {
        if (queue.size() == 1) {
            if (loc.contains(queue.getFirst())) {
                // Check that arrow has all its transitions for the state in the queue
                if (arrow.get(new Tuple<>(queue.getFirst(), new Tuple<>("Crit-NonCrit", "goNonCrit"))) != null) {
                    if (arrow.get(new Tuple<>(queue.getFirst(), new Tuple<>("Wait-Crit", "goCrit"))) != null) {
                        if (arrow.get(new Tuple<>(queue.getFirst(), new Tuple<>("NonCrit-Wait", "goWait"))) != null) {
                            return;
                        }
                    }
                }
            }
        }

        Location curLoc = queue.pop();

//        int j = curLoc.j;
        for (int i = 0; i < numberofProcesses; ++i) {
            Location next = new Location(curLoc.j, curLoc.data, curLoc.levels.clone());
            ProgramState state = next.updateProcessState(i);

            Tuple<Location, Tuple<String, String>> nextArrow = null;

            switch (state) {
                case NON_CRIT: {
                    nextArrow = new Tuple<>(curLoc, new Tuple<>("Crit-NonCrit", "goNonCrit"));

                    if (!arrow.containsKey(nextArrow)) {
                        arrow.put(nextArrow, next);
                    }

                    break;
                }
                case WAIT: {
                    nextArrow = new Tuple<>(curLoc, new Tuple<>("NonCrit-Wait", "goWait"));

                    if (!arrow.containsKey(nextArrow)) {
                        arrow.put(nextArrow, next);
                    }

                    break;
                }
                case CRIT: {
                    nextArrow = new Tuple<>(curLoc, new Tuple<>("Wait-Crit", "goCrit"));

                    if (!arrow.containsKey(nextArrow)) {
                        arrow.put(nextArrow, next);
                    }

                    break;
                }
            }

            if (!loc.contains(next)) {
                loc.add(new Location(next.j, (ArrayList<Process>) next.data.clone(), next.levels.clone()));

                if (!queue.contains(next)) {
                    queue.add(next);
                } else {
                    queue.remove(next);
                }
            }
        }

        recur(queue, numberofProcesses);
    }

    public static ProgramGraph getInstance(int numProcesses) {
        ProgramGraph pg = new ProgramGraph();

        pg.generate(numProcesses);

        return pg;
    }
}
