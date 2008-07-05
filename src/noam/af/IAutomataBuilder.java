package noam.af;

public interface IAutomataBuilder {
	void addState(String id);
	void addTerminal(String t);
	void addTransition(Transition t);
	void setInitialState(String id);
	void addFinalState(String id);	
}
