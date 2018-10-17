package task2;

public class Process {
    public Location parent;
    public ProgramState programState;
    public int level;
    public int number;

    public Process(int n, Location parent) {
        this.parent = parent;
        this.number = n;
        programState = ProgramState.NON_CRIT;
        level = 0;
    }

    public Process(Location parent, ProgramState programState, int level, int number) {
        this.parent = parent;
        this.programState = programState;
        this.level = level;
        this.number = number;
    }

    public void setNextState() {
        switch (programState)
        {
            case CRIT:
            {
                this.level = 0;
                programState = ProgramState.NON_CRIT;

                break;
            }
            case NON_CRIT:
            {
                programState = ProgramState.WAIT;

                break;
            }
            case WAIT:
            {
                for (Process t : parent.data)
                {
                    if (t.level < parent.j)
                    {
                        programState = ProgramState.CRIT;
                        return;
                    }
                }

                if (parent.levels[parent.j].number != this.number)
                {
                    programState = ProgramState.CRIT;
                }

                break;
            }
        }
    }
}
