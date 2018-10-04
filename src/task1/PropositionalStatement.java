package task1;

import java.util.HashSet;
import java.util.Iterator;

public class PropositionalStatement
{
	private Symbol base;
	private Proposition result;
	
	private Tuple<PropositionalStatement, PropositionalStatement> children;
	
	public PropositionalStatement(Symbol base, Proposition result, Tuple<PropositionalStatement, PropositionalStatement> children)
	{
		this.base = base;
		this.result = result;
		this.children = children;
	}
	
	public void setChildValues(HashSet<Proposition> propositions)
	{
		if (base.equals(Symbol.STATEMENT))
		{
			for (Proposition p : propositions)
			{
				if (result.name.equals(p.name))
				{
					result = p;
					return;
				}
			}
		}
		else
		{
			children.first.setChildValues(propositions);
			
			if (children.second != null)
			{
				children.second.setChildValues(propositions);
			}
		}
	}
	
	public boolean evaluate()
	{
		switch (base)
		{
			case OR:
			{
				return children.first.evaluate() || children.second.evaluate();
			}
			case AND:
			{
				return children.first.evaluate() && children.second.evaluate();
			}
			case NOT:
			{
				return !children.first.evaluate();
			}
			case IMPLICATION:
			{
				return !children.first.evaluate() || children.second.evaluate();
			}
			case STATEMENT:
			{
				return result.evaluate();
			}
			default:
			{
				return false;
			}
		}
	}
	
	public Symbol getBase()
	{
		return base;
	}
	
	public Tuple<PropositionalStatement, PropositionalStatement> getChildren()
	{
		return children;
	}
	
	public Proposition getResult()
	{
		return result;
	}
	
	public boolean violates(HashSet<Proposition> ap)
	{
		if (base.equals(Symbol.STATEMENT))
		{
			Iterator<Proposition> iter = ap.iterator();
			int i = 0;
			
			while (iter.hasNext())
			{
				Proposition p = iter.next();
				
				if (result.name.equals(p.name))
				{
					return false;
				}
				else
				{
					if (i == ap.size())
					{
						return true;
					}
					
					++i;
				}
			}
		}
		else
		{
			if (children.second == null)
			{
				return children.first.violates(ap);
			}
			else
			{
				return children.first.violates(ap) || children.second.violates(ap);
			}
		}
		
		return true;
	}
}
