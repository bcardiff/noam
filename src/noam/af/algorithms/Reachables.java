package noam.af.algorithms;


import static noam.utils.IteratorHelper.intersect;

import java.util.HashSet;
import java.util.Iterator;

import noam.IO;
import noam.af.AF;
import noam.af.InvalidStateException;
import noam.af.Transition;

public class Reachables implements AF {

	AF inner;
	HashSet<String> reachables;

	public Reachables(AF automaton) {
		this.inner = automaton;

		reachables = new HashSet<String>();
		reachables.add(inner.getInitialState());
		closureReachables();
	}

	private void closureReachables() {
		boolean modified = true;
		while (modified) {
			modified = false;
			for (String s : reachables) {
				Iterator<Transition> it = inner.getTransitions(s);
				while (it.hasNext()) {
					Transition t = it.next();
					if (reachables.add(t.getTo())) {
						modified = true;
					}
				}
				if (modified)
					break;
			}
		}
	}

	
	public Iterator<String> getFinalStates() {
		return intersect(inner.getFinalStates(), reachables);
	}

	
	public String getInitialState() {
		return inner.getInitialState();
	}

	
	public Iterator<String> getStates() {
		return reachables.iterator();
	}

	
	public Iterator<Transition> getTransitions(String from) {
		if (!reachables.contains(from))
			throw new InvalidStateException(from);

		return inner.getTransitions(from);
	}

	
	public Iterator<Transition> getTransitions(String from, String label) {
		if (!reachables.contains(from))
			throw new InvalidStateException(from);
		
		return inner.getTransitions(from, label);
	}

	
	public Iterator<String> getAlphabet() {
		return inner.getAlphabet();
	}

	@Override
	public String toString() {
		return IO.print(this);
	}
}
