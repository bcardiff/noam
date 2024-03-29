package noam.af;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import noam.IO;

public class AFND implements AF {

	// cada nodo tiene asociada una lista de adyacencia en la cual la transicion
	// es la key
	private HashMap<String, HashMap<String, Set<String>>> graph;
	private String initialSt;
	private Set<String> finalSts;
	private List<String> alphabet;

	public AFND() {
		graph = new HashMap<String, HashMap<String, Set<String>>>();
		finalSts = new HashSet<String>();
		alphabet = new LinkedList<String>();
	}

	public void addNode(String name) {
		if (!graph.containsKey(name))
			graph.put(name, new HashMap<String, Set<String>>());
	}

	public void addFinalState(String name) {
		finalSts.add(name);
	}

	public void addTransition(String src, String trans, String dst) {
		if (!graph.containsKey(src))
			graph.put(src, new HashMap<String, Set<String>>());
		if (!graph.get(src).containsKey(trans))
			graph.get(src).put(trans, new HashSet<String>());
		graph.get(src).get(trans).add(dst);
	}

	public void setInitialState(String initial) {
		initialSt = initial;
	}

	public Iterator<String> getFinalStates() {
		return finalSts.iterator();
	}

	public String getInitialState() {
		return initialSt;
	}

	public Iterator<String> getStates() {
		return graph.keySet().iterator();
	}

	public Iterator<Transition> getTransitions(String from) {
		LinkedList<Transition> r = new LinkedList<Transition>();
		for (Entry<String, Set<String>> entry : graph.get(from).entrySet()) {
			for (String dest : entry.getValue()) {
				r.add(new Transition(from, entry.getKey(), dest));
			}
		}

		return r.iterator();
	}

	public Iterator<Transition> getTransitions(String from, String label) {
		LinkedList<Transition> r = new LinkedList<Transition>();
		HashMap<String, Set<String>> transitions = graph.get(from);
		if (transitions.containsKey(label)) {
			for (String dest : transitions.get(label)) {
				r.add(new Transition(from, label, dest));
			}
		}
		return r.iterator();
	}

	public void addTerminal(String t) {
		alphabet.add(t);
	}

	
	public Iterator<String> getAlphabet() {
		return alphabet.iterator();
	}
	
	@Override
	public String toString() {
		return IO.print(this);
	}
}
