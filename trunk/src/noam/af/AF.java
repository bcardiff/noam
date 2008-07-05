package noam.af;

import java.util.Iterator;

public interface AF {
	Iterator<String> getStates();
	String getInitialState();
	Iterator<String> getFinalStates();
	Iterator<Transition> getTransitions(String from);
	Iterator<Transition> getTransitions(String from, String label);
	
}
