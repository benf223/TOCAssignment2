package task1;

/**
 * ID: 15906291, 15904719
 * Names: Ben Fisher, Jethro Tuburan
 * Class that describes a pair of Generic objects
 */
public class Tuple<T, P>
{
	public final T first;
	public final P second;
	
	public Tuple(T first, P second)
	{
		this.first = first;
		this.second = second;
	}
	
	@Override
	public int hashCode()
	{
		return first.hashCode() + second.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Tuple)
		{
			return ((Tuple) obj).first.equals(first) && ((Tuple) obj).second.equals(second);
		}
		
		return false;
	}
}
