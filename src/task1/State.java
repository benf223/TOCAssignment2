package task1;

/**
 * ID: 15906291, 15904719
 * Names: Ben Fisher, Jethro Tuburan
 * Wrapper class for a string for clarity
 */
public class State
{
	public final String name;
	
	public State(String name)
	{
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof State)
		{
			return ((State) obj).name.equals(this.name);
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}
}
