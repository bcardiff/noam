package noam.af.algorithms;

import static noam.utils.IteratorHelper.addAll;
import static noam.utils.IteratorHelper.difference;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import noam.IO;
import noam.af.AF;
import noam.af.Transition;
import noam.utils.Function;
import noam.utils.IteratorMapping;
import noam.utils.JoinIterator;
import noam.utils.SingletonIterator;

public class Complete implements AF {

	private final AF inner;
	private String deadState;

	private final Function<Transition, String> getLabels;

	public Complete(AF automata) {
		this.inner = automata;
		computeDeadState();

		getLabels = new Function<Transition, String>() {
			public String apply(Transition s) {
				return s.getLabel();
			}
		};
	}

	public static AF ensureComplete(AF automata) {
		if (isComplete(automata))
			return automata;
		else
			return new Complete(automata);
	}

	private static boolean isComplete(AF automata) {
		Iterator<String> itStates = automata.getStates();
		while (itStates.hasNext()) {
			String state = itStates.next();
			Iterator<String> itSymbol = automata.getAlphabet();
			while (itSymbol.hasNext()) {
				if (!automata.getTransitions(state, itSymbol.next()).hasNext())
					return false;
			}
		}
		return true;
	}

	private void computeDeadState() {
		StringBuilder buffer = new StringBuilder();
		Iterator<String> it = inner.getStates();
		buffer.append("Z");
		while (it.hasNext()) {
			buffer.append(it.next());
		}
		deadState = buffer.toString();
	}

	public Iterator<String> getAlphabet() {
		return inner.getAlphabet();
	}

	public Iterator<String> getFinalStates() {
		return inner.getFinalStates();
	}

	public String getInitialState() {
		return inner.getInitialState();
	}

	public Iterator<String> getStates() {
		return new JoinIterator<String>(inner.getStates(),
				new SingletonIterator<String>(deadState));
	}

	public Iterator<Transition> getTransitions(final String from) {
		Set<String> existingLabels = new HashSet<String>();
		if (!from.equals(deadState)) {
			addAll(existingLabels, new IteratorMapping<Transition, String>(
					inner.getTransitions(from), getLabels));
		}
		Iterator<String> labelsToBeAdded = difference(inner.getAlphabet(),
				existingLabels);

		Iterator<Transition> transitionsToDeadState = new IteratorMapping<String, Transition>(
				labelsToBeAdded, new Function<String, Transition>() {
					public Transition apply(String s) {
						return new Transition(from, s, deadState);
					}
				});

		if (from.equals(deadState)) {
			return transitionsToDeadState;
		}

		return new JoinIterator<Transition>(inner.getTransitions(from),
				transitionsToDeadState);
	}

	public Iterator<Transition> getTransitions(String from, String label) {
		if (from.equals(deadState)) {
			return new SingletonIterator<Transition>(new Transition(from,
					label, deadState));
		} else {
			Iterator<Transition> it = inner.getTransitions(from, label);
			if (it.hasNext())
				return it;
			else
				return new SingletonIterator<Transition>(new Transition(from,
						label, deadState));
		}
	}

	public String getDeadState() {
		return deadState;
	}

	@Override
	public String toString() {
		return IO.print(this);
	}
}
