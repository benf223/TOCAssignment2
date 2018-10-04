package task1;

public class Tuple <T, P>
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
