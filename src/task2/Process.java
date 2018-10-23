package task2;

public class Process
{
	public Location parent;
	public ProgramState programState;
	public int number;
	
	public Process(int n, Location parent)
	{
		this.parent = parent;
		this.number = n;
		programState = ProgramState.NON_CRIT;
	}
	
	public Process(Location parent, ProgramState programState, int number)
	{
		this.parent = parent;
		this.programState = programState;
		this.number = number;
	}
	
	public void setNextState()
	{
		switch (programState)
		{
			case CRIT:
			{
				programState = ProgramState.NON_CRIT;
				
				break;
			}
			case NON_CRIT:
			{
				this.parent.queue.addLast(this);
				programState = ProgramState.WAIT;
				
				break;
			}
			case WAIT:
			{
				if (parent.queue.peek().equals(this))
				{
					for (Process t : parent.data)
					{
						if (t.programState.equals(ProgramState.CRIT))
						{
							return;
						}
					}
					
					parent.queue.pop();
					
					programState = ProgramState.CRIT;
				}
				
				break;
			}
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Process)
		{
			if (((Process) obj).programState.equals(this.programState))
			{
				if (((Process) obj).number == this.number)
				{
					if (((Process) obj).parent.hashCode() == this.parent.hashCode())
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
