package noam.af.algorithms;

import static noam.utils.IteratorHelper.addAll;
import static noam.utils.IteratorHelper.difference;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import noam.af.AF;
import noam.af.Transition;
import noam.utils.Function;
import noam.utils.StringHelper;

public class Minimization implements AF {

	AF inner;
	List<HashSet<String>> statesPartition;
	TreeMap<String, Integer> statesToPartition;
	Function<Transition, Transition> innerToThis;

	// requires Complete, Deterministict AF
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
		
		// fill statesToPartition
		statesToPartition = new TreeMap<String, Integer>();
		for (int i = 0; i < statesPartition.size(); i++) {
			statesToPartition.put(StringHelper.asString(i), i);
		}
		
		innerToThis = new Function<Transition, Transition>(){
			public Transition apply(Transition s) {
				return new Transition(
						StringHelper.asString(classOf(s.getFrom())), 
						s.getLabel(), 
						StringHelper.asString(classOf(s.getTo())));
			}			
		};
	}

	private void computeStatesPartition() {
		int countOfStates = statesPartition.size();
		int prevCountOfStates;
		do {
			prevCountOfStates = countOfStates;
			computeNextStatesPartition();
			countOfStates = statesPartition.size();
		} while (countOfStates != prevCountOfStates);
	}

	private void computeNextStatesPartition() {
		LinkedList<HashSet<String>> nextPartition = new LinkedList<HashSet<String>>();

		for (HashSet<String> eqClass : statesPartition) {
			// since we will take elements from 'eqClass' we need to 
			// to do it in a copy, because we need to check in which 
			// eqClass a state was.
			HashSet<String> eqClassCopy = new HashSet<String>(eqClass);
			while (eqClassCopy.size() > 0) {
				HashSet<String> newEqClass = new HashSet<String>();
				final String e = takeOne(eqClassCopy);
				newEqClass.add(e);
				moveAllThat(newEqClass, eqClassCopy, new Function<String,Boolean>(){
					public Boolean apply(String s) {
						Iterator<String> symbolIt = inner.getAlphabet();
						while (symbolIt.hasNext()){
							String symbol = symbolIt.next();
							if (classAfter(s, symbol) != classAfter(e, symbol))
								return false;
						}
						return true;
					}
				});
				nextPartition.add(newEqClass);
			}
		}
		
		statesPartition = nextPartition;
	}

	private int classAfter(String s, String symbol) {
		Iterator<Transition> it = inner.getTransitions(s, symbol);
		if (!it.hasNext())
			throw new RuntimeException("automata not complete");
		Transition t = it.next();
		if (it.hasNext())
			throw new RuntimeException("automata not deterministic");
		
		return classOf(t.getTo());
	}
	
	private int classOf(String innerState){
		int i = 0;
		for (HashSet<String> eqClass : statesPartition) {
			if (eqClass.contains(innerState))
				return i;
			i++;
		}
		
		throw new RuntimeException("target state not in partition");		
	}

	private void moveAllThat(HashSet<String> dest,
			HashSet<String> source, Function<String, Boolean> pred) {
		Iterator<String> it = source.iterator();
		while(it.hasNext()){
			String c = it.next();
			if (pred.apply(c)){
				it.remove();
				dest.add(c);
			}
		}
	}

	private String takeOne(Set<String> s) {
		Iterator<String> it = s.iterator();
		String res = it.next();
		it.remove();
		return res;
	}

	public Iterator<String> getFinalStates() {
		// we use a set to remove duplicates
		HashSet<String> res = new HashSet<String>();
		Iterator<String> innerFinals = inner.getFinalStates();
		while (innerFinals.hasNext()){
			res.add(StringHelper.asString(classOf(innerFinals.next())));
		}
		return res.iterator();
	}

	public String getInitialState() {
		return StringHelper.asString(classOf(inner.getInitialState()));
	}

	public Iterator<String> getStates() {
		return statesToPartition.keySet().iterator();
	}

	public Iterator<Transition> getTransitions(String from) {
		List<Iterator<Transition>> trans = new LinkedList<Iterator<Transition>>();
		HashSet<String> eqClass = statesPartition.get(statesToPartition.get(from).intValue());
		for (String innerState : eqClass) {
			trans.add(inner.getTransitions(innerState));
		}
		return joinTransition(trans);
	}

	public Iterator<Transition> getTransitions(String from, String label) {
		List<Iterator<Transition>> trans = new LinkedList<Iterator<Transition>>();
		HashSet<String> eqClass = statesPartition.get(statesToPartition.get(from).intValue());
		for (String innerState : eqClass) {
			trans.add(inner.getTransitions(innerState, label));
		}
		return joinTransition(trans);
	}

	private Iterator<Transition> joinTransition(List<Iterator<Transition>> trans) {
		HashSet<Transition> res = new HashSet<Transition>();
		for (Iterator<Transition> it : trans) {
			while(it.hasNext()){
				res.add(innerToThis.apply(it.next()));
			}
		}
		return res.iterator();
	}

	public Iterator<String> getAlphabet() {
		return inner.getAlphabet();
	}

}
