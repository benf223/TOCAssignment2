package task1;


/**
 * ID: 15906291, 15904719
 * Names: Ben Fisher, Jethro Tuburan
 * Class that describes a Proposition and it's name with a boolean value.
 */
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
	
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Proposition)
		{
			return ((Proposition) obj).name.equals(this.name);
		}
		
		return false;
	}
}
