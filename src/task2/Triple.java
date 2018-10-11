package task2;

public class Triple {
    public Location parent;
    public ProgramState programState;
    public int pn;
    public int yn;
    private int number;

    public Triple(int n, Location parent) {
        this.parent = parent;
        programState = ProgramState.NON_CRIT;
        this.pn = n;
        this.yn = n;
        this.number = n;
    }

    public void setNextState() {
        switch (programState)
        {
            case CRIT:
            {
                pn = 0;
                programState = ProgramState.NON_CRIT;

                break;
            }
            case WAIT:
            {
                programState = ProgramState.CRIT;

                break;
            }
            case NON_CRIT:
            {
                parent.getTriple(this.number + 1).yn
                programState = ProgramState.WAIT;

                break;
            }
        }
    }
}
