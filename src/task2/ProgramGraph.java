package task2;

import task1.Tuple;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

/**
 * ID: 15906291, 15904719
 * Names: Ben Fisher, Jethro Tuburan
 * Class that describes a Program Graph using the HashSets as sets to describe the system
 * This class implements an iterative method of generating an interleaving of n processes of a Peterson's Mutual exclusion algorithm
 * This class also provides getters so that it can be transformed into an equivalent Transition System
 */
public class ProgramGraph
{
	private int numberOfProcesses;
	
	private HashSet<Location> loc;
	private HashSet<String> act;
	private HashSet<ThreeTuple<Location, Tuple<String, String>, Location>> arrow;
	private HashSet<Location> loc0;
	
	public ProgramGraph()
	{
		this.loc = new HashSet<>();
		this.act = new HashSet<>();
		this.act.add("goWait");
		this.act.add("goCrit");
		this.act.add("goNonCrit");
		this.arrow = new HashSet<>();
		this.loc0 = new HashSet<>();
	}
	
	public void generate(int numberOfProcesses)
	{
		this.numberOfProcesses = numberOfProcesses;
		
		LinkedList<Tuple<Location, Location>> queue = new LinkedList<>();
		
		do
		{
			createLocations(queue, numberOfProcesses);
		} while (!queue.isEmpty());
		
		Stack<Location> toCreate = new Stack<>();
		toCreate.addAll(loc);
		
		while (!toCreate.empty())
		{
			createArrow(toCreate, numberOfProcesses);
		}
	}
	
	private void createLocations(LinkedList<Tuple<Location, Location>> queue, int numberOfProcesses)
	{
		Tuple<Location, Location> current;
		Location curLoc;
		
		if (queue.size() == 0)
		{
			if (loc.size() == 0)
			{
				curLoc = new Location(numberOfProcesses);
				loc0.add(curLoc);
				loc.add(curLoc);
				
				for (int i = 0; i < numberOfProcesses; ++i)
				{
					Location next = new Location(curLoc.data, (LinkedList<Process>) curLoc.queue.clone());
					
					next.updateProcessState(i);
					queue.add(new Tuple<>(curLoc, next));
				}
			}
		}
		else
		{
			current = queue.pop();
			curLoc = current.second;
			loc.add(curLoc);
			
			for (int i = 0; i < numberOfProcesses; ++i)
			{
				Location next = new Location(curLoc.data, (LinkedList<Process>) curLoc.queue.clone());
				
				next.updateProcessState(i);
				
				if (!loc.contains(next))
				{
					queue.addLast(new Tuple<>(curLoc, next));
				}
			}
		}
	}
	
	private void createArrow(Stack<Location> toDo, int numberOfProcesses)
	{
		if (toDo.size() == 0)
		{
			return;
		}
		
		Location curLoc = toDo.pop();
		
		for (int i = 0; i < numberOfProcesses; ++i)
		{
			Location clonedCurrent = new Location(curLoc.data, (LinkedList<Process>) curLoc.queue.clone());
			
			ProgramState state = clonedCurrent.updateProcessState(i);
			
			if (!state.equals(curLoc.data.get(i).programState))
			{
				ThreeTuple<Location, Tuple<String, String>, Location> nextArrow = new ThreeTuple<>(curLoc, clonedCurrent);
				
				switch (state)
				{
					case NON_CRIT:
					{
						nextArrow.second = new Tuple<>("Crit-NonCrit", "goNonCrit");
						
						break;
					}
					case WAIT:
					{
						nextArrow.second = new Tuple<>("NonCrit-Wait", "goWait");
						
						break;
					}
					case CRIT:
					{
						nextArrow.second = new Tuple<>("Wait-Crit", "goCrit");
						
						break;
					}
				}
				
				arrow.add(nextArrow);
			}
			
		}
	}
	
	public static ProgramGraph getInstance(int numProcesses)
	{
		ProgramGraph pg = new ProgramGraph();
		
		pg.generate(numProcesses);
		
		return pg;
	}
	
	public HashSet<Location> getLocations()
	{
		return loc;
	}
	
	public HashSet<Location> getLoc0()
	{
		return loc0;
	}
	
	public HashSet<String> getActions()
	{
		return act;
	}
	
	public HashSet<ThreeTuple<Location, Tuple<String, String>, Location>> getArrow()
	{
		return arrow;
	}
	
	public int getNumberOfProcesses()
	{
		return numberOfProcesses;
	}
}
