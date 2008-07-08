package noam.af.algorithms;

import static noam.utils.IteratorHelper.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import noam.af.AF;
import noam.af.Transition;

public class Minimization implements AF {

	AF inner;
	List<HashSet<String>> statesPartition;

	public Minimization(AF inner) {
		this.inner = inner;
		
		// initial states partitios {S/F, F}
		statesPartition = new LinkedList<HashSet<String>>();
		HashSet<String> innerFinalStates = new HashSet<String>();
		HashSet<String> restOfStates = new HashSet<String>();
		addAll(innerFinalStates, inner.getFinalStates());
		addAll(restOfStates, difference(inner.getStates(), innerFinalStates));
		statesPartition.add(restOfStates);
		statesPartition.add(innerFinalStates);
		
		computeStatesPartition();
	}

	private void computeStatesPartition() {
		LinkedList<HashSet<String>> nextPartition = new LinkedList<HashSet<String>>();
		// TODO
	}

		
	public Iterator<String> getFinalStates() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getInitialState() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Iterator<String> getStates() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Iterator<Transition> getTransitions(String from) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Iterator<Transition> getTransitions(String from, String label) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Iterator<String> getAlphabet() {
		// TODO Auto-generated method stub
		return null;
	}

}
