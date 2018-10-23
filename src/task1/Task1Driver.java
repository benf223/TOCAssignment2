package task1;

/**
 * ID: 15906291, 15904719
 * Names: Ben Fisher, Jethro Tuburan
 * Class that contains a main method to drive the functionality of the first task
 */
public class Task1Driver
{
	public static void main(String[] args)
	{
		// Statement equivalent to !(a ^ b)
		PropositionalStatement statement3 = new PropositionalStatement(Symbol.STATEMENT, new Proposition("a", false), null);
		PropositionalStatement statement2 = new PropositionalStatement(Symbol.STATEMENT, new Proposition("b", true), null);
		PropositionalStatement statement1 = new PropositionalStatement(Symbol.AND, null, new Tuple<>(statement2, statement3));
		PropositionalStatement statement = new PropositionalStatement(Symbol.NOT, null, new Tuple<>(statement1, null));
		
		try
		{
			System.out.println("Satisfies statement: " + TransitionSystem.getInstance().checkInvariant(statement));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// Statement equivalent to (!(a ^ b) -> c) v !c
		PropositionalStatement baseA = new PropositionalStatement(Symbol.STATEMENT, new Proposition("a", false), null);
		PropositionalStatement baseB = new PropositionalStatement(Symbol.STATEMENT, new Proposition("b", false), null);
		PropositionalStatement baseC = new PropositionalStatement(Symbol.STATEMENT, new Proposition("c", false), null);
		PropositionalStatement aAndB = new PropositionalStatement(Symbol.AND, null, new Tuple<>(baseA, baseB));
		PropositionalStatement notAAndB = new PropositionalStatement(Symbol.NOT, null, new Tuple<>(aAndB, null));
		PropositionalStatement notAAndBImpliesC = new PropositionalStatement(Symbol.IMPLICATION, null, new Tuple<>(notAAndB, baseC));
		PropositionalStatement notC = new PropositionalStatement(Symbol.NOT, null, new Tuple<>(baseC, null));
		PropositionalStatement top = new PropositionalStatement(Symbol.OR, null, new Tuple<>(notAAndBImpliesC, notC));
		
		System.out.println();
		
		try
		{
			System.out.println("Satisfies statement: " + TransitionSystem.getInstance().checkInvariant(top));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
