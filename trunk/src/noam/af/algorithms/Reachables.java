package noam.af.algorithms;


import static noam.utils.IteratorHelper.intersect;

import java.util.HashSet;
import java.util.Iterator;

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

	@Override
	public Iterator<String> getFinalStates() {
		return intersect(inner.getFinalStates(), reachables);
	}

	@Override
	public String getInitialState() {
		return inner.getInitialState();
	}

	@Override
	public Iterator<String> getStates() {
		return reachables.iterator();
	}

	@Override
	public Iterator<Transition> getTransitions(String from) {
		if (!reachables.contains(from))
			throw new InvalidStateException(from);

		return inner.getTransitions(from);
	}

	@Override
	public Iterator<Transition> getTransitions(String from, String label) {
		if (!reachables.contains(from))
			throw new InvalidStateException(from);
		
		return inner.getTransitions(from, label);
	}

}
