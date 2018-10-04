package task1;

public class Proposition
{
	public final String name;
	private final boolean set;
	
	public Proposition(String name, boolean set)
	{
		this.name = name;
		this.set = set;
	}
	
	public boolean evaluate()
	{
		return set;
	}
}
