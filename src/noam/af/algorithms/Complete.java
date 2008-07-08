package noam.af.algorithms;

import static noam.utils.IteratorHelper.*;
import java.util.Iterator;
import java.util.LinkedList;

import noam.af.AF;
import noam.af.Transition;
import noam.utils.JoinIterator;
import noam.utils.SingletonIterator;

public class Complete implements AF {

	private final AF inner;
	private String deadState;

	public Complete(AF automata) {
		this.inner = automata;
		computeDeadState();
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

	@Override
	public Iterator<String> getAlphabet() {
		return inner.getAlphabet();
	}

	@Override
	public Iterator<String> getFinalStates() {
		return inner.getFinalStates();
	}

	@Override
	public String getInitialState() {
		return inner.getInitialState();
	}

	@Override
	public Iterator<String> getStates() {
		return new JoinIterator<String>(inner.getStates(),
				new SingletonIterator<String>(deadState));
	}

	@Override
	public Iterator<Transition> getTransitions(String from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Transition> getTransitions(String from, String label) {
		Iterator<Transition> it = inner.getTransitions(from, label);
		if (it.hasNext())
			return it;
		else
			return new SingletonIterator<Transition>(new Transition(from, label, deadState));
	}

}
