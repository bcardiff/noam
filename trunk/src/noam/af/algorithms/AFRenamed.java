package noam.af.algorithms;

import java.util.Iterator;
import noam.af.AF;
import noam.af.Transition;
import noam.utils.Function;
import noam.utils.IteratorMapping;

public class AFRenamed implements AF {

	private AF automaton;
	private Function<String, String> appendStates;
	private Function<Transition, Transition> appendTrans;

	
	public AFRenamed(AF a, final String preffix) {
		automaton = a;
		appendStates = new Function<String, String>() {
			public String apply(String s) {
				return preffix + s;
			}
		};
		appendTrans = new Function<Transition, Transition>() {
			public Transition apply(Transition t) {
				return new Transition(preffix + t.getFrom(), t.getLabel(),
						preffix + t.getTo());
			}
		};
	}

	public Iterator<String> getFinalStates() {

		Iterator<String> it = automaton.getFinalStates();

		return new IteratorMapping<String, String>(it, appendStates);
	}

	public String getInitialState() {
		return appendStates.apply(automaton.getInitialState());
	}

	public Iterator<String> getStates() {

		Iterator<String> it = automaton.getStates();

		return new IteratorMapping<String, String>(it, appendStates);
	}

	public Iterator<Transition> getTransitions(String from) {

		Iterator<Transition> it = automaton.getTransitions(from);

		return new IteratorMapping<Transition, Transition>(it, appendTrans);
	}

	public Iterator<Transition> getTransitions(String from, String label) {

		Iterator<Transition> it = automaton.getTransitions(from, label);
		return new IteratorMapping<Transition, Transition>(it, appendTrans);
	}

}
