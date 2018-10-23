package task2;

import task1.*;

import java.util.ArrayList;

public class Task2Driver
{
	public static final int NUMBER_OF_PROCESSES = 2;
	
	public static void main(String[] args)
	{
		System.out.println("Estimated maximum states: " + calculateStateCount());
		
		long time = System.nanoTime();
		ProgramGraph graph = ProgramGraph.getInstance(NUMBER_OF_PROCESSES);
		System.out.println("Time to create ProgramGraph (ms): " + (System.nanoTime() - time) / 1000000f);
		
		TransitionSystem ts = TransitionSystem.generateFromPG(graph);
		
		System.out.println("Number of states: " + ts.stateCount());
		
		try
		{
			PropositionalStatement mutexInvariant = createMutexInvariant();
			
			boolean result = ts.checkInvariant(mutexInvariant);
			
			System.out.println("Satisfies statement: " + result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		while (true);
	}
	
	private static int calculateStateCount()
	{
		int output = (int) Math.pow(3, NUMBER_OF_PROCESSES);
		
		return output + (3 * output * output);
	}
	
	private static PropositionalStatement createMutexInvariant()
	{
		ArrayList<PropositionalStatement> bases = new ArrayList<>();
		
		for (int i = 0; i < NUMBER_OF_PROCESSES; ++i)
		{
			bases.add(new PropositionalStatement(Symbol.STATEMENT, new Proposition(ProgramState.getLabel(ProgramState.CRIT) + (i + 1), false), null));
		}
		
		PropositionalStatement left = createLeft((ArrayList) bases.clone());
		PropositionalStatement right = createRight((ArrayList) bases.clone());
		
		return negate(new PropositionalStatement(Symbol.AND, null, new Tuple<>(left, right)));
	}
	
	private static PropositionalStatement createLeft(ArrayList<PropositionalStatement> bases)
	{
		PropositionalStatement top = null;
		
		while (bases.size() >= 2)
		{
			PropositionalStatement first = bases.get(0);
			PropositionalStatement second = bases.get(1);
			
			bases.remove(first);
			bases.remove(second);
			
			top = new PropositionalStatement(Symbol.OR, null, new Tuple<>(first, second));
			bases.add(top);
		}
		
		return top;
	}
	
	private static PropositionalStatement createRight(ArrayList<PropositionalStatement> bases)
	{
		ArrayList<PropositionalStatement> pairs = new ArrayList<>();
		
		while (bases.size() >= 2)
		{
			pairs.addAll(createPairs(bases.remove(0), bases));
		}
		
		pairs = negate(pairs);
		
		PropositionalStatement top = null;
		
		while (pairs.size() >= 2)
		{
			PropositionalStatement first = pairs.remove(0);
			PropositionalStatement second = pairs.remove(0);
			
			top = new PropositionalStatement(Symbol.AND, null, new Tuple<>(first, second));
			pairs.add(top);
		}
		
		return top;
	}
	
	private static ArrayList<PropositionalStatement> createPairs(PropositionalStatement base, ArrayList<PropositionalStatement> toPair)
	{
		ArrayList<PropositionalStatement> pairs = new ArrayList<>();
		
		for (PropositionalStatement p : toPair)
		{
			pairs.add(new PropositionalStatement(Symbol.AND, null, new Tuple<>(base, p)));
		}
		
		return pairs;
	}
	
	private static ArrayList<PropositionalStatement> negate(ArrayList<PropositionalStatement> toNegate)
	{
		ArrayList<PropositionalStatement> negate = new ArrayList<>();
		
		for (PropositionalStatement p : toNegate)
		{
			negate.add(new PropositionalStatement(Symbol.NOT, null, new Tuple<>(p, null)));
		}
		
		return negate;
	}
	
	private static PropositionalStatement negate(PropositionalStatement toNegate)
	{
		return new PropositionalStatement(Symbol.NOT, null, new Tuple<>(toNegate, null));
	}
}
