package noam.af.algorithms;

import java.util.HashMap;
import java.util.Iterator;

import noam.af.AF;
import noam.af.Terminal;
import noam.af.Transition;
import noam.er.ER;
import noam.er.ERChoice;
import noam.er.ERClosure;
import noam.er.ERConcat;
import noam.er.EREmpty;
import noam.er.ERTerminal;
import noam.utils.IteratorHelper;

public class AFDtoER {

	private AF automaton;

	private HashMap<Integer, String> enumStates;

	public AFDtoER(AF automaton) {
		this.automaton = automaton;
		this.enumStates = new HashMap<Integer, String>();

		Iterator<String> it = automaton.getStates();
		int i = 1;
		while (it.hasNext()) {
			enumStates.put(i, it.next());
			i++;
		}
	}

	public ER convert() {

		ER res = new EREmpty();

		// Cuento los estados finales
		int n = IteratorHelper.countOf(automaton.getFinalStates());

		Iterator<String> iter = automaton.getFinalStates();

		while (iter.hasNext()) {

			ER newChoice = rijK(enumStates.get(1), iter.next(), n);

			if (res instanceof EREmpty) {
				res = newChoice;
			} else {
				res = new ERChoice(res, newChoice);
			}
		}

		return res;
	}

	private ER rijK(String from, String to, int k) {

		// Caso base
		if (k == 0) {
			return basicStep(from, to);
		}

		String kState = enumStates.get(k);

		ER firstPath = rijK(from, kState, k - 1);
		ER lastPath = rijK(kState, to, k - 1);
		ER loop = doClosure(rijK(kState, kState, k - 1));
		ER direct = rijK(from, to, k - 1);


		return doChoice(doConcat(doConcat(firstPath, loop), lastPath), direct);
	}


	private ER basicStep(String from, String to) {

		ER res = new EREmpty();

		Iterator<Transition> it = automaton.getTransitions(from);

		while (it.hasNext()) {
			Transition t = it.next();

			if (t.getTo().equals(to)) {
				res = doChoice( res, new ERTerminal(t.getLabel()));
			}
		}

		if (from.equals(to)) {
			doChoice(res, new ERTerminal(Terminal.LAMBDA));
		}

		return res;
	}
	

	private ER doConcat(ER erLeft, ER erRigth) {
		
		if ( erLeft instanceof EREmpty ){
			return erRigth;
		}
		
		if ( erRigth instanceof EREmpty ){
			return erLeft;
		}
		
		return new ERConcat( erLeft , erRigth );
		
	}

	private ER doChoice(ER erLeft, ER erRigth){
				
		if ( erLeft instanceof EREmpty ){
			return erRigth;
		}
		
		if ( erRigth instanceof EREmpty ){
			return erLeft;
		}
		
		return new ERChoice( erLeft , erRigth );
		
	}
	
	private ER doClosure(ER erInner){
		
		if ( erInner instanceof EREmpty ){
			return erInner;
		}
		return new ERClosure(erInner);
		
	}
	
}