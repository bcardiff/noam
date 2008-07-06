package noam.af.internal;

import noam.af.AFND;
import noam.af.IAutomataBuilder;
import noam.af.Transition;

public class AFNDBuilder implements IAutomataBuilder {

	AFND output;

	public AFNDBuilder() {
		output = new AFND();
	}

	@Override
	public void addFinalState(String id) {
		output.addFinalState(id);
	}

	@Override
	public void addState(String id) {
		output.addNode(id);
	}

	@Override
	public void addTerminal(String t) {
		// TODO ?
	}

	@Override
	public void addTransition(Transition t) {
		output.addTransition(t.getFrom(), t.getTo(), t.getTo());
	}

	@Override
	public void setInitialState(String id) {
		output.setInitialState(id);
	}

	public AFND getAutomata() {
		return output;
	}
}
