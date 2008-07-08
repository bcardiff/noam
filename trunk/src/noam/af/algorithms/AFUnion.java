package noam.af.algorithms;

import java.util.Iterator;
import java.util.Set;

import noam.af.AF;
import noam.af.AFND;
import noam.af.Transition;
import noam.af.internal.AFNDBuilder;
import noam.utils.Function;
import noam.utils.IteratorMapping;
import noam.utils.JoinIterator;

public class AFUnion implements AF {

	//Es un asco... lo commiteo para seguir despues.
	private AFND automaton;
	
	
	public AFUnion(AF a1, AF a2) {
		AF a1renamed = new AFRenamed(a1, "First");
		AF a2renamed = new AFRenamed(a2, "Second");
		
		automaton = joinAF(a1renamed, a2renamed);
	}

	private AFND joinAF(AF a1, AF a2) {
		AFNDBuilder afndBuilder = new AFNDBuilder();
		Iterator<String> iter= new JoinIterator<String>(a1.getStates(), a2.getStates());
		
		while (iter.hasNext()) {
			afndBuilder.addState(iter.next());
		}
		
		return null;
	}

	public void setInitialState(String state) {
		automaton.setInitialState(state);
	}
	
	public void addState(String state) {
		automaton.addNode(state);
	}

	public Iterator<String> getFinalStates() {

		return automaton.getFinalStates();
	}

	public String getInitialState() {
		
		return automaton.getInitialState();
	}

	public Iterator<String> getStates() {

		return automaton.getStates();
	}

	public Iterator<Transition> getTransitions(String from) {

		return automaton.getTransitions(from);

	}

	public Iterator<Transition> getTransitions(String from, String label) {

		return automaton.getTransitions(from, label);
	}

	public Iterator<String> getAlphabet() {

		return automaton.getAlphabet();
	}

}
