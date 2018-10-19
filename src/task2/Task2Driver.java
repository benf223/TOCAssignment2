package task2;

import task1.*;

import java.util.ArrayList;

public class Task2Driver
{
	public static final int NUMBER_OF_PROCESSES = 5;

	public static void main(String[] args)
	{
		ProgramGraph graph = ProgramGraph.getInstance(NUMBER_OF_PROCESSES);

		TransitionSystem ts = TransitionSystem.generateFromPG(graph, NUMBER_OF_PROCESSES);

		ArrayList<PropositionalStatement> bases = new ArrayList<>();

		for (int i = 0; i < NUMBER_OF_PROCESSES; ++i)
		{
			bases.add(new PropositionalStatement(Symbol.STATEMENT, new Proposition(ProgramState.getLabel(ProgramState.CRIT) + (i + 1), false), null));
		}

		ArrayList<PropositionalStatement> negation = new ArrayList<>();

		for (PropositionalStatement statement : bases)
		{
			negation.add(new PropositionalStatement(Symbol.NOT, null, new Tuple<>(statement, null)));
		}

		PropositionalStatement top = null;

		while (negation.size() >= 2)
		{
			PropositionalStatement first = negation.get(0);
			PropositionalStatement second = negation.get(1);

			negation.remove(first);
			negation.remove(second);

			top = new PropositionalStatement(Symbol.OR, null, new Tuple<>(first, second));
			negation.add(top);
		}

		try {
			System.out.println(ts.checkInvariant(top));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
