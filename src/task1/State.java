package task1;

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
