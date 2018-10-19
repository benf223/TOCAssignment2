package task1;

import task2.Location;
import task2.ProgramGraph;
import task2.ProgramState;
import task2.ThreeTuple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class TransitionSystem
{
	private HashSet<State> s;
	private HashSet<String> act;
	private HashMap<Tuple<State, String>, State> arrow;
	private HashSet<Proposition> ap;
	private HashSet<State> i;
	private HashMap<State, HashSet<Proposition>> l;

	public TransitionSystem(){
		this(new HashSet<>(), new HashSet<>(), new HashMap<>(), new HashSet<>(), new HashSet<>(), new HashMap<>());
	}
	
	public TransitionSystem(HashSet<State> s, HashSet<String> act, HashMap<Tuple<State, String>, State> arrow, HashSet<Proposition> ap, HashSet<State> i, HashMap<State, HashSet<Proposition>> l)
	{
		this.s = s;
		this.act = act;
		this.arrow = arrow;
		this.ap = ap;
		this.i = i;
		this.l = l;
	}

	public boolean checkInvariant(PropositionalStatement statement) throws Exception
	{
		return checkInvariant(statement, null, null);
	}

    public boolean checkInvariant(PropositionalStatement statement, Stack<State> states, HashSet<State> done) throws Exception
	{
		if (states == null)
		{
			if (statement.violates(ap))
			{
				throw new Exception("AP is violated by the statement");
			}
			
			states = new Stack<>();
			states.push(i.iterator().next());
		}
		
		if (done == null)
		{
			done = new HashSet<>();
		}
		
		if (done.size() == states.size())
		{
			return true;
		}
		
		HashSet<Proposition> baseValues = l.get(states.peek());
		PropositionalStatement evaluation = new PropositionalStatement(statement.getBase(), statement.getResult(), statement.getChildren());
		evaluation.setChildValues(baseValues);
		
		if (evaluation.evaluate())
		{
			State current = states.pop();
			done.add(current);
			
			for (String s : act)
			{
				State next = arrow.get(new Tuple<>(current, s));
				
				if (next != null)
				{
					if (!done.contains(next))
					{
						if (!states.contains(next))
						{
							states.push(next);
						}
					}
				}
			}
			
			return checkInvariant(statement, states, done);
		}
		
		// Can change done to a stack and print it out if required
		System.out.println("Counter example: ");
		State violation = states.pop();
		HashSet<Proposition> label = l.get(violation);
		
		System.out.println(violation.name);
		String labels = "";
		
		for (Proposition p : label)
		{
			labels += p.name + ": " + p.evaluate() + "; ";
		}
		
		System.out.println(labels);
		
		return false;
	}
	
	public static TransitionSystem getInstance()
	{
		HashSet<State> states = new HashSet<>();
		
		for (int i = 0; i < 16; ++i)
		{
			states.add(new State("" + ((char) ('a' + i))));
		}
		
		HashSet<String> act = new HashSet<>();
		act.add("alpha");
		act.add("beta");
		act.add("gamma");
		
		HashMap<Tuple<State, String>, State> arrow = new HashMap<>();
		
		arrow.put(new Tuple<>(new State("a"), "alpha"), new State("e"));
		arrow.put(new Tuple<>(new State("a"), "beta"), new State("b"));
		arrow.put(new Tuple<>(new State("b"), "alpha"), new State("c"));
		arrow.put(new Tuple<>(new State("c"), "beta"), new State("g"));
		arrow.put(new Tuple<>(new State("c"), "gamma"), new State("f"));
		arrow.put(new Tuple<>(new State("e"), "alpha"), new State("i"));
		arrow.put(new Tuple<>(new State("e"), "beta"), new State("f"));
		arrow.put(new Tuple<>(new State("e"), "gamma"), new State("j"));
		arrow.put(new Tuple<>(new State("f"), "alpha"), new State("k"));
		arrow.put(new Tuple<>(new State("g"), "beta"), new State("d"));
		arrow.put(new Tuple<>(new State("h"), "gamma"), new State("g"));
		arrow.put(new Tuple<>(new State("i"), "beta"), new State("n"));
		arrow.put(new Tuple<>(new State("i"), "gamma"), new State("m"));
		arrow.put(new Tuple<>(new State("j"), "gamma"), new State("n"));
		arrow.put(new Tuple<>(new State("k"), "alpha"), new State("h"));
		arrow.put(new Tuple<>(new State("k"), "beta"), new State("o"));
		arrow.put(new Tuple<>(new State("k"), "gamma"), new State("l"));
		arrow.put(new Tuple<>(new State("o"), "alpha"), new State("p"));
		arrow.put(new Tuple<>(new State("p"), "alpha"), new State("l"));
		
		HashSet<Proposition> ap = new HashSet<>();
		ap.add(new Proposition("a", false));
		ap.add(new Proposition("b", false));
		ap.add(new Proposition("c", false));
		
		HashSet<State> i = new HashSet<>();
		i.add(new State("a"));
		
		HashMap<State, HashSet<Proposition>> l = new HashMap<>();
		
		Proposition pA = new Proposition("a", true);
		Proposition pB = new Proposition("b", true);
		Proposition pC = new Proposition("c", true);
		Proposition pAf = new Proposition("a", false);
		Proposition pBf = new Proposition("b", false);
		Proposition pCf = new Proposition("c", false);
		
		HashSet<Proposition> a = new HashSet<>();
		a.add(pA);
		a.add(pBf);
		a.add(pCf);
		
		HashSet<Proposition> b = new HashSet<>();
		b.add(pAf);
		b.add(pB);
		b.add(pCf);
		
		HashSet<Proposition> c = new HashSet<>();
		c.add(pAf);
		c.add(pBf);
		c.add(pC);
		
		HashSet<Proposition> ab = new HashSet<>();
		ab.add(pA);
		ab.add(pB);
		ab.add(pCf);
		
		HashSet<Proposition> ac = new HashSet<>();
		ac.add(pA);
		ac.add(pBf);
		ac.add(pC);
		
		HashSet<Proposition> bc = new HashSet<>();
		bc.add(pAf);
		bc.add(pB);
		bc.add(pC);
		
		HashSet<Proposition> abc = new HashSet<>();
		abc.add(pA);
		abc.add(pB);
		abc.add(pC);
		
		l.put(new State("a"), ap);
		l.put(new State("b"), ab);
		l.put(new State("c"), c);
		l.put(new State("d"), ac);
		l.put(new State("e"), bc);
		l.put(new State("f"), c);
		l.put(new State("g"), ap);
		l.put(new State("h"), b);
		l.put(new State("i"), ap);
		l.put(new State("j"), a);
		l.put(new State("k"), bc);
		l.put(new State("l"), a);
		l.put(new State("m"), ab);
		l.put(new State("n"), ab);
		l.put(new State("o"), bc);
		l.put(new State("p"), abc);
		
		return new TransitionSystem(states, act, arrow, ap, i, l);
	}

	public static TransitionSystem generateFromPG(ProgramGraph graph, int numberOfProcesses) {
		TransitionSystem system = new TransitionSystem();

		for (int i = 0; i < numberOfProcesses; ++i)
		{
			system.ap.add(new Proposition(ProgramState.getLabel(ProgramState.NON_CRIT) + (i + 1), false));
			system.ap.add(new Proposition(ProgramState.getLabel(ProgramState.WAIT) + (i + 1), false));
			system.ap.add(new Proposition(ProgramState.getLabel(ProgramState.CRIT) + (i + 1), false));
		}

		for (Location l : graph.getLocations())
		{
			State toAdd = new State(l.toString());

			system.s.add(toAdd);
			HashSet<Proposition> fL = l.getAP();

			if (fL.size() != system.ap.size())
			{
				for (Proposition p : system.ap)
				{
					if (!fL.contains(p))
					{
						fL.add(p);
					}
				}
			}

			system.l.put(toAdd, fL);
		}

		for (Location l : graph.getLoc0())
		{
			system.i.add(new State(l.toString()));
		}

		system.act = graph.getActions();

		// Arrows

		for (ThreeTuple<Location, Tuple<String, String>, Location> arr : graph.getArrow())
		{
			State from = new State(arr.first.toString());
			String act = arr.second.second;
			State to = new State(arr.third.toString());

			system.arrow.put(new Tuple<>(from, act), to);

		}

		return system;
	}
}
