package task1;

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
		PropositionalStatement statement4 = new PropositionalStatement(Symbol.STATEMENT, new Proposition("b", false), null);
		statement3 = new PropositionalStatement(Symbol.STATEMENT, new Proposition("a", false), null);
		statement2 = new PropositionalStatement(Symbol.STATEMENT, new Proposition("c", false), null);
		statement1 = new PropositionalStatement(Symbol.AND, null, new Tuple<>(statement3, statement4));
		PropositionalStatement statement5 = new PropositionalStatement(Symbol.NOT, null, new Tuple<>(statement1, null));
		statement = new PropositionalStatement(Symbol.IMPLICATION, null, new Tuple<>(statement5, statement2));
		
		PropositionalStatement statement6 = new PropositionalStatement(Symbol.STATEMENT, new Proposition("c", false), null);
		PropositionalStatement statement7 = new PropositionalStatement(Symbol.NOT, null, new Tuple<>(statement6, null));
		PropositionalStatement statement8 = new PropositionalStatement(Symbol.OR, null, new Tuple<>(statement, statement7));
		
		System.out.println();
		
		try
		{
			System.out.println("Satisfies statement: " + TransitionSystem.getInstance().checkInvariant(statement8));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
