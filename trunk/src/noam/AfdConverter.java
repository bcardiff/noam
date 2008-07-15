package noam;

import java.io.Reader;
import java.util.Iterator;

import noam.af.AF;
import noam.af.Terminal;
import noam.af.Transition;

public class AfdConverter extends AfConverter {

	public AfdConverter(String input) {
		super(input);
	}

	public AfdConverter(Reader reader) {
		super(reader);
	}

	@Override
	public boolean isFormalismOk() {
		return super.isFormalismOk() && isDeterministic(formalism);
	}
	
	private static boolean isDeterministic(AF automata){
		Iterator<String> states = automata.getStates();
		while (states.hasNext()) {
			String s = (String) states.next();
			Iterator<Transition> transitions = automata.getTransitions(s);
			while (transitions.hasNext()){
				Transition transition = transitions.next();
				if (transition.getLabel().equals(Terminal.LAMBDA))
					return false; // Lambda transition
				Iterator<Transition> transWithSameTerminal = automata.getTransitions(s, transition.getLabel());
				transWithSameTerminal.next(); // skip first
				if ( transWithSameTerminal.hasNext() )
					return false; // Non deterministic transition
			}
		}
		return true;
	}
	
	@Override
	public AF toAFD() {
		return formalism;
	}
}
