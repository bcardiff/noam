package noam.af.algorithms;

import static noam.utils.IteratorHelper.addAll;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;

import noam.af.AF;
import noam.af.InvalidStateException;
import noam.af.Terminal;
import noam.af.Transition;

public class Determination implements AF {

	private final AF inner;
	private HashMap<String, HashSet<String>> lambdaClosures; // lambda
	// closures of
	// AF's inner
	// states.
	private HashMap<String, HashSet<String>> subsetsOfStates;
	private List<String> finalStates;
	private String initialState;

	public Determination(AF automaton) {
		this.inner = automaton;
		computeLambdaClosures();
		computeStates();
		computeFinalStates();
	}

	private void computeLambdaClosures() {
		lambdaClosures = new HashMap<String, HashSet<String>>();
		Iterator<String> it = inner.getStates();
		while (it.hasNext()) {
			String s = it.next();
			HashSet<String> closure = new HashSet<String>();
			closure.add(s);
			lambdaClosure(inner, closure);
			lambdaClosures.put(s, closure);
		}
	}

	private void computeStates() {
		subsetsOfStates = new HashMap<String, HashSet<String>>();

		// set initial state
		initialState = stateFactory();
		HashSet<String> Qd = lambdaClosures.get(inner.getInitialState());

		// list of nodes pending to traverse
		Queue<HashSet<String>> pendingStates = new LinkedList<HashSet<String>>();
		pendingStates.add(Qd);

		while (!pendingStates.isEmpty()) {
			HashSet<String> ps = pendingStates.remove();
			// if exists in subsetsOfStates, continue;
			if (getMappedState(ps) != null)
				continue;

			subsetsOfStates.put(stateFactory(), ps);

			Set<String> effectiveLabels = computeEffectiveTransitionsLabels(
					inner, ps);
			effectiveLabels.remove(Terminal.LAMBDA);

			// Evolve ps through each effective state
			// lambda closure it using individual lambda closures
			// store it as pending
			for (String label : effectiveLabels) {
				HashSet<String> evolved = evolve(inner, ps, label);
				addLambdaClosure(evolved);
				if (!evolved.isEmpty())
					pendingStates.add(evolved);
			}
		}
	}

	private String getMappedState(HashSet<String> states) {
		for (Entry<String, HashSet<String>> entry : subsetsOfStates.entrySet()) {
			if (hasSameElements(entry.getValue(), states))
				return entry.getKey();
		}
		return null;
	}

	private static boolean hasSameElements(HashSet<String> seta,
			HashSet<String> setb) {
		if (seta.size() != setb.size())
			return false;

		for (String a : seta) {
			if (!setb.contains(a))
				return false;
		}

		return true;
	}

	private static HashSet<String> evolve(AF automaton, HashSet<String> states,
			String label) {
		HashSet<String> res = new HashSet<String>();
		for (String s : states) {
			Iterator<Transition> it = automaton.getTransitions(s, label);
			while (it.hasNext()) {
				res.add(it.next().getTo());
			}
		}
		return res;
	}

	private void addLambdaClosure(HashSet<String> states) {
		Queue<String> pending = new LinkedList<String>(states);
		while (!pending.isEmpty()) {
			String s = pending.remove();
			states.addAll(lambdaClosures.get(s));
		}
	}

	private static HashSet<String> computeEffectiveTransitionsLabels(
			AF automaton, HashSet<String> states) {
		HashSet<String> res = new HashSet<String>();
		for (String s : states) {
			Iterator<Transition> it = automaton.getTransitions(s);
			while (it.hasNext()) {
				res.add(it.next().getLabel());
			}
		}
		return res;
	}

	private static void lambdaClosure(AF automaton, HashSet<String> states) {
		HashSet<String> leaves = new HashSet<String>();
		leaves.addAll(states);
		while (!leaves.isEmpty()) {
			// pick one leaf
			String s = leaves.iterator().next();
			leaves.remove(s);

			// add leaf to resultant states.
			states.add(s);

			// add accessibles trough state s.
			Iterator<Transition> it = automaton.getTransitions(s,
					Terminal.LAMBDA);
			while (it.hasNext()) {
				Transition t = it.next();
				if (!states.contains(t.getTo())) {
					leaves.add(t.getTo());
				}
			}
		}
	}

	private void computeFinalStates() {
		finalStates = new LinkedList<String>();

		Set<String> innerFinalStates = new HashSet<String>();
		addAll(innerFinalStates, inner.getFinalStates());

		for (Entry<String, HashSet<String>> entry : subsetsOfStates.entrySet()) {
			if (intersects(entry.getValue(), innerFinalStates)) {
				finalStates.add(entry.getKey());
			}
		}
	}

	private static boolean intersects(Set<String> seta, Set<String> setb) {
		for (String a : seta) {
			if (setb.contains(a))
				return true;
		}
		return false;
	}

	private String stateFactory() {
		return String.valueOf((char) (subsetsOfStates.size() + (int) 'A'));
	}

	
	public Iterator<String> getFinalStates() {
		return finalStates.iterator();
	}

	
	public String getInitialState() {
		return initialState;
	}

	
	public Iterator<String> getStates() {
		return subsetsOfStates.keySet().iterator();
	}

	
	public Iterator<Transition> getTransitions(String from) {
		LinkedList<Transition> res = new LinkedList<Transition>();

		if (!subsetsOfStates.containsKey(from))
			throw new InvalidStateException(from);
		HashSet<String> statesFrom = subsetsOfStates.get(from);
		HashSet<String> labels = computeEffectiveTransitionsLabels(inner,
				statesFrom);
		// no incluir las lambda		
		labels.remove(Terminal.LAMBDA);		

		for (String label : labels) {
			Transition t = getTransition(from, label);
			if (t != null){
				res.add(t);
			}
		}

		return res.iterator();
	}

	
	public Iterator<Transition> getTransitions(String from, String label) {
		LinkedList<Transition> res = new LinkedList<Transition>();
		if (label.equals(Terminal.LAMBDA)) {
			return res.iterator();
		}
		if (!subsetsOfStates.containsKey(from))
			throw new InvalidStateException(from);

		Transition t = getTransition(from, label);

		if (t != null) {
			res.add(t);
		}
		return res.iterator();
	}

	private Transition getTransition(String from, String label) {
		HashSet<String> statesFrom = subsetsOfStates.get(from);
		HashSet<String> evolved = evolve(inner, statesFrom, label);
		addLambdaClosure(evolved);
		String toState = getMappedState(evolved);

		if (toState != null) {
			return new Transition(from, label, toState);
		} else {
			return null;
		}
	}

	
	public Iterator<String> getAlphabet() {
		return inner.getAlphabet();
	}

}
