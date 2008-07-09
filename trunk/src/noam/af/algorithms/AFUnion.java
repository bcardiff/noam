package noam.af.algorithms;

import static noam.utils.IteratorHelper.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import noam.af.AF;
import noam.af.Transition;
import noam.utils.IteratorHelper;
import noam.utils.JoinIterator;

public class AFUnion implements AF {

	// Ya no es un asco, porque lo arreglo Brian.
	private AF a;
	private AF b;
	private Set<String> finalStates;
	private String initialState;
	private HashSet<String> aStates;
	private HashSet<String> bStates;

	public AFUnion(AF a1, AF a2) {
		a = a1;
		b = a2;
		aStates = new HashSet<String>();
		addAll(aStates, a.getStates());
		bStates = new HashSet<String>();
		addAll(bStates, b.getStates());

		finalStates = new HashSet<String>();
	}

	public void setInitialState(String state) {
		this.initialState = state;
	}

	public Iterator<String> getFinalStates() {
		return finalStates.iterator();
	}

	public void addFinalState(String state) {
		finalStates.add(state);
	}

	public String getInitialState() {
		return initialState;
	}

	public Iterator<String> getStates() {

		Set<String> states = new HashSet<String>();
		IteratorHelper.addAll(states, a.getStates());
		IteratorHelper.addAll(states, b.getStates());

		return states.iterator();
	}

	public Iterator<Transition> getTransitions(String from) {
		if (aStates.contains(from) && bStates.contains(from)) {
			return new JoinIterator<Transition>(a.getTransitions(from),
					b.getTransitions(from));
		} else if (aStates.contains(from)) {
			return a.getTransitions(from);
		} else if (bStates.contains(from)) {
			return b.getTransitions(from);
		} else {
			throw new RuntimeException();
		}
	}

	public Iterator<Transition> getTransitions(String from, String label) {
		if (aStates.contains(from) && bStates.contains(from)) {
			return new JoinIterator<Transition>(a.getTransitions(from, label),
					b.getTransitions(from, label));
		} else if (aStates.contains(from)) {
			return a.getTransitions(from, label);
		} else if (bStates.contains(from)) {
			return b.getTransitions(from, label);
		} else {
			throw new RuntimeException();
		}
	}

	public Iterator<String> getAlphabet() {

		Set<String> alphabet = new HashSet<String>();
		IteratorHelper.addAll(alphabet, a.getAlphabet());
		IteratorHelper.addAll(alphabet, b.getAlphabet());

		return alphabet.iterator();
	}

}
