package noam.er;

import noam.af.AF;
import noam.af.Terminal;
import noam.af.Transition;
import noam.af.algorithms.AFRenamed;
import noam.af.internal.AFNDBuilder;

//TODO: Hacer todo :P . Cuando los automatas se puedan componer.

public class ERToAutomata implements IVisitor {

	public AF visit(ERChoice e) {
		// TODO Auto-generated method stub
		return null;
	}

	public AF visit(ERClosure e) {
		// TODO Auto-generated method stub
		return null;
	}

	public AF visit(ERConcat e) {

		AF autLeft = new AFRenamed((AF) e.getErLeft().accept(this), "L");
		AF autRight = new AFRenamed((AF) e.getErRight().accept(this), "R");
		
		
		
		addTransition(new Transition("A", Terminal.LAMBDA , "Z"));
		
		return ab.getAutomata();
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
