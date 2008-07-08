package noam.af.internal;

import noam.af.AFND;
import noam.af.IAutomataBuilder;
import noam.af.Transition;

public class AFNDBuilder implements IAutomataBuilder {

	AFND output;

	public AFNDBuilder() {
		output = new AFND();
	}

	
	public void addFinalState(String id) {
		output.addFinalState(id);
	}

	
	public void addState(String id) {
		output.addNode(id);
	}

	
	public void addTerminal(String t) {
		// TODO ?
	}

	
	public void addTransition(Transition t) {
		output.addTransition(t.getFrom(), t.getLabel(), t.getTo());
	}

	
	public void setInitialState(String id) {
		output.setInitialState(id);
	}

	public AFND getAutomata() {
		return output;
	}
}
