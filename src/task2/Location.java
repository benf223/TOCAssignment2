package task2;


import java.util.ArrayList;

public class Location {
    //p
    public ArrayList<Process> data;

    //y
    public Process[] levels;

    public int j;

    public Location(int n) {
        data = new ArrayList<>();
        j = 0;

        levels = new Process[n];

        for (int i =0; i < n; ++i) {
            data.add(new Process(i, this));
        }
    }

    public Location(int j, ArrayList<Process> data, Process[] levels)
    {
        this.j = j;
        this.data = new ArrayList<>();

        for (Process p : data)
        {
            this.data.add(new Process(this, p.programState, p.level, p.number));
        }

        this.levels = levels;
    }

    public ProgramState updateProcessState(int i) {
        if (data.get(i).programState.equals(ProgramState.NON_CRIT))
        {
            data.get(i).level = j;
            levels[j] = data.get(i);

            ++j;

            if (j >= data.size())
            {
                j = 0;
            }
        }

        data.get(i).setNextState();

        return data.get(i).programState;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Location)
        {
            for (int i = 0; i < data.size(); ++i)
            {
                if (!((Location) obj).data.get(i).programState.equals(data.get(i).programState))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int sum = 0;

        for (Process p : data)
        {
            sum += p.programState.hashCode();
        }

        return sum;
    }
}
