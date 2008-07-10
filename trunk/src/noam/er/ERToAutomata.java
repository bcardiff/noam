package noam.er;

import java.util.Iterator;

import noam.af.AF;
import noam.af.Terminal;
import noam.af.Transition;
import noam.af.algorithms.AFRenamed;
import noam.af.algorithms.AFUnion;
import noam.af.internal.AFNDBuilder;
import noam.utils.JoinIterator;
import noam.utils.StringHelper;

public class ERToAutomata implements IVisitor {

	public AF visit(ERChoice e) {
		AF autUp = new AFRenamed((AF) e.getErLeft().accept(this), StringHelper.addPrefix("U"), StringHelper.removePrefix("U"));
		AF autDown = new AFRenamed((AF) e.getErRight().accept(this),  StringHelper.addPrefix("D"), StringHelper.removePrefix("D"));
		
		AFNDBuilder aBuilder = new AFNDBuilder();
		// nuevos estados: inicial y final.
		aBuilder.addState("A");
		aBuilder.addState("Z");
		
		// Pongo un eje LAMBDA desde el nuevo inicial hacia los antiguos iniciales.
		aBuilder.addState(autUp.getInitialState());
		aBuilder.addState(autDown.getInitialState());		
		aBuilder.addTransition(new Transition("A", Terminal.LAMBDA, autUp.getInitialState()));
		aBuilder.addTransition(new Transition("A", Terminal.LAMBDA, autDown.getInitialState()));
		
		
		// Pongo ejes LAMBDA desde los viejos finales hasta el nuevo final.
		Iterator<String> finalStates = new JoinIterator<String>(autUp.getFinalStates(), autDown.getFinalStates());
		
		while (finalStates.hasNext()) {
			String curState = finalStates.next();
			aBuilder.addState(curState);
			aBuilder.addTransition(new Transition(curState, Terminal.LAMBDA, "Z"));
		}
		
		AFUnion res = new AFUnion(aBuilder.getAutomata(),new AFUnion(autUp, autDown));
		
		res.setInitialState("A");
		res.addFinalState("Z");
				
		return res;
	}

	public AF visit(ERClosure e) {
		
		AF inner =(AF) e.getErInner().accept(this);
		String initial = inner.getInitialState();
		
		// En este automata meto las transiciones agregadas:
		// Desde los estados finales hasta el inicial por LAMBDA.
		AFNDBuilder ab = new AFNDBuilder();
		ab.addState(initial);		
		
		Iterator<String> it = inner.getFinalStates();
		
		while (it.hasNext()) {
			String curState = it.next();
			ab.addState(curState);
			ab.addTransition(new Transition(curState, Terminal.LAMBDA, initial));
		}
				
		AFUnion res = new AFUnion(inner, ab.getAutomata());
		
		// El estado inicial es el mismo, y además es final.
		res.setInitialState(inner.getInitialState());
		res.addFinalState(inner.getInitialState());
		
		return res;
	}

	public AF visit(ERConcat e) {

		AF autLeft = new AFRenamed((AF) e.getErLeft().accept(this), StringHelper.addPrefix("L"), StringHelper.removePrefix("L"));
		AF autRight = new AFRenamed((AF) e.getErRight().accept(this),  StringHelper.addPrefix("R"), StringHelper.removePrefix("R"));
		

		AFNDBuilder aBuilder = new AFNDBuilder();
		String initRigthState = autRight.getInitialState();
		aBuilder.addState(initRigthState);
		
		Iterator<String> itLeft = autLeft.getFinalStates();
		
		while (itLeft.hasNext()) {
			String curState = itLeft.next();
			aBuilder.addState(curState);
			aBuilder.addTransition(new Transition(curState, Terminal.LAMBDA, initRigthState));
		}
		
		
		AFUnion res = new AFUnion(autLeft, new AFUnion(autRight, aBuilder.getAutomata()));
		res.setInitialState(autLeft.getInitialState());
		
		Iterator<String> it = autRight.getFinalStates();
		while (it.hasNext()) {
			res.addFinalState(it.next());
			
		}
				
		return res;
	}

	public AF visit(EREmpty e) {
		
		AFNDBuilder ab = new AFNDBuilder();
		ab.setInitialState("A");
		ab.addFinalState("Z");
				
		return ab.getAutomata();
	}

	public AF visit(ERLambda e) {

		AFNDBuilder ab = new AFNDBuilder();
		ab.setInitialState("A");
		ab.addFinalState("Z");
		ab.addTransition(new Transition("A", Terminal.LAMBDA , "Z"));
		
		return ab.getAutomata();
	}

	public Object visit(ERTerminal e) {

		AFNDBuilder ab = new AFNDBuilder();
		ab.setInitialState("A");
		ab.addFinalState("Z");
		ab.addTransition(new Transition("A", e.getTerminal() , "Z"));
		
		return ab.getAutomata();
	}



}
