package noam.af.algorithms;

import noam.IO;
import noam.af.AF;
import noam.af.Transition;
import noam.af.internal.AFNDBuilder;
import noam.er.ER;
import noam.er.ERChoice;
import noam.er.ERClosure;
import noam.er.ERConcat;
import noam.er.ERTerminal;
import noam.er.ERToAutomata;
import noam.er.grammar.ERParser;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AFDtoERFixture {

	
	//No anda. pero no se si esta bien el test
	@Test
	public void test1(){
		AFNDBuilder ab = new AFNDBuilder();
		ab.addState("A");
		ab.addState("B");
				
		ab.setInitialState("A");
		ab.addFinalState("B");
		
		ab.addTransition(new Transition("A", "a", "B"));
		ab.addTransition(new Transition("A", "b", "B"));
		ab.addTransition(new Transition("B", "b", "A"));
		ab.addTransition(new Transition("B", "a", "B"));
		
		AF a = new Determination(ab.getAutomata());
		System.out.println(IO.printDot(a));
		
		ER er = IO.parseER("(a|b).(b.a|b.b|a)*");
		
		ER traducida= (new AFDtoER(a)).convert();
		
		assertEquals(er, traducida);
	}
	
}
