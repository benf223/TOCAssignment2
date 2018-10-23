package task2;

/**
 * ID: 15906291, 15904719
 * Names: Ben Fisher, Jethro Tuburan
 * Enum that describes the possible states a Process could be in
 */
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
