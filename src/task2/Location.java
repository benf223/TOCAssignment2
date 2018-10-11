package task2;


import java.util.ArrayList;

public class Location {
    private ArrayList<Triple> data;

    public Location(int n) {
        data = new ArrayList<>();

        for (int i =0; i < n; ++i) {
            data.add(new Triple(i, this));
        }
    }

    public Triple getTriple(int n){
        return data.get(n);
    }

    public ProgramState setProgramState(int i) {
        Triple current = data.get(i);
        current.setNextState();
        data.set(i, current);
        return current.programState;
    }
}
