package noam.af.algorithms;

import java.util.Iterator;

import noam.af.AF;
import noam.af.Transition;
import noam.utils.Function;
import noam.utils.IteratorMapping;

public class AFRenamed implements AF {

	private AF automaton;
	private Function<Transition, Transition> fromInnerTrans;
	private final Function<String, String> fromInner;
	private final Function<String, String> toInner;

	public AFRenamed(AF inner, Function<String, String> fromInner,
			Function<String, String> toInner) {
		automaton = inner;
		this.fromInner = fromInner;
		this.toInner = toInner;
		this.fromInnerTrans = new Function<Transition, Transition>() {
			public Transition apply(Transition t) {
				return new Transition(AFRenamed.this.fromInner.apply(t
						.getFrom()), t.getLabel(), AFRenamed.this.fromInner
						.apply(t.getTo()));
			}
		};
	}

	public Iterator<String> getFinalStates() {
		Iterator<String> it = automaton.getFinalStates();
		return new IteratorMapping<String, String>(it, fromInner);
	}

	public String getInitialState() {
		return fromInner.apply(automaton.getInitialState());
	}

	public Iterator<String> getStates() {
		Iterator<String> it = automaton.getStates();
		return new IteratorMapping<String, String>(it, fromInner);
	}

	public Iterator<Transition> getTransitions(String from) {
		Iterator<Transition> it = automaton.getTransitions(toInner.apply(from));
		return new IteratorMapping<Transition, Transition>(it, fromInnerTrans);
	}

	public Iterator<Transition> getTransitions(String from, String label) {
		Iterator<Transition> it = automaton.getTransitions(toInner.apply(from), label);
		return new IteratorMapping<Transition, Transition>(it, fromInnerTrans);
	}

	public Iterator<String> getAlphabet() {
		return automaton.getAlphabet();
	}
}
