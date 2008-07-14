package noam.af.algorithms;

import java.util.Iterator;
import java.util.TreeMap;

import noam.IO;
import noam.af.AF;
import noam.af.Transition;
import noam.utils.Function;
import noam.utils.IteratorMapping;
import noam.utils.StringHelper;

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

	public static AF CanonicalNamed(AF af) {
		final TreeMap<String, String> fromInner = new TreeMap<String, String>();
		final TreeMap<String, String> toInner = new TreeMap<String, String>();

		Iterator<String> itStates = af.getStates();
		while (itStates.hasNext()) {
			String innerState = itStates.next();
			String outerState = StringHelper.asString(fromInner.size());
			fromInner.put(innerState, outerState);
			toInner.put(outerState, innerState);
		}

		return new AFRenamed(af, new Function<String, String>() {
			public String apply(String s) {
				return fromInner.get(s);
			}
		}, new Function<String, String>() {
			public String apply(String s) {
				return toInner.get(s);
			}
		});
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
		Iterator<Transition> it = automaton.getTransitions(toInner.apply(from),
				label);
		return new IteratorMapping<Transition, Transition>(it, fromInnerTrans);
	}

	public Iterator<String> getAlphabet() {
		return automaton.getAlphabet();
	}

	@Override
	public String toString() {
		return IO.print(this);
	}
}
