package task2;

public enum ProgramState
{
	NON_CRIT,
	WAIT,
	CRIT;
	
	public static String getLabel(ProgramState state)
	{
		switch (state)
		{
			case NON_CRIT:
			{
				return "Non-Crit.";
			}
			case CRIT:
			{
				return "Crit.";
			}
			case WAIT:
			{
				return "Wait.";
			}
		}
		
		return "";
	}
}
