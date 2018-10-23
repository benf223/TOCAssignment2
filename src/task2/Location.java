package task2;


import task1.Proposition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * ID: 15906291, 15904719
 * Names: Ben Fisher, Jethro Tuburan
 * Class that describes a Location in a Program graph.
 * This class is designed to represent an interleaving of processes of the Peterson's mutual exclusion algorithm.
 * This class implements the algorithm slightly differently with a queue but the functionality appears identical.
 */
public class Location
{
	public ArrayList<Process> data;
	
	public LinkedList<Process> queue;
	
	public Location(int n)
	{
		data = new ArrayList<>();
		
		queue = new LinkedList<>();
		
		for (int i = 0; i < n; ++i)
		{
			data.add(new Process(i, this));
		}
	}
	
	public Location(ArrayList<Process> data, LinkedList<Process> queue)
	{
		this.data = new ArrayList<>();
		
		for (Process p : data)
		{
			this.data.add(new Process(this, p.programState, p.number));
		}
		
		this.queue = new LinkedList<>();
		
		if (queue.size() > 0)
		{
			for (Process p : queue)
			{
				this.queue.addLast(this.data.get(this.data.indexOf(p)));
			}
		}
	}
	
	public ProgramState updateProcessState(int i)
	{
		data.get(i).setNextState();
		
		return data.get(i).programState;
	}
	
	@Override
	public String toString()
	{
		String string = "";
		
		for (Process p : data)
		{
			string += ProgramState.getLabel(p.programState);
		}
		
		if (!queue.isEmpty())
		{
			string += "-" + data.indexOf(queue.peek());
		}
		
		return string;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		
		if (obj instanceof Location)
		{
			for (int i = 0; i < data.size(); ++i)
			{
				if (!((Location) obj).data.get(i).programState.equals(data.get(i).programState))
				{
					
					return false;
				}
			}
			
			if (((Location) obj).queue.size() == this.queue.size())
			{
				if (this.queue.size() > 0)
				{
					if (((Location) obj).queue.peek().equals(this.queue.peek()))
					{
						return true;
					}
				}
				else
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		int sum = 0;
		
		for (Process p : data)
		{
			sum += p.programState.hashCode();
		}
		
		return sum;
	}
	
	public HashSet<Proposition> getAP()
	{
		HashSet<Proposition> propositions = new HashSet<>();
		
		for (int i = 0; i < data.size(); ++i)
		{
			propositions.add(new Proposition(ProgramState.getLabel(data.get(i).programState) + (i + 1), true));
		}
		
		return propositions;
	}
}
