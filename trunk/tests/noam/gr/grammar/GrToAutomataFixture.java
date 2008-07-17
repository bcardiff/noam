package noam.gr.grammar;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import noam.IO;
import noam.af.AF;
import noam.af.Terminal;
import noam.af.Transition;
import noam.gr.Grammar;
import noam.gr.algorithms.GrToAutomata;
import noam.utils.IteratorHelper;

import org.junit.Test;

public class GrToAutomataFixture {

	@Test
	public void test1() {
		Grammar gr = IO.parseGr("<(S),(),((S,LAMBDA)),S>");
		AF a = GrToAutomata.convert(gr);

		IteratorHelper.assertSameElements(a.getAlphabet());

		assertEquals("S", a.getInitialState());
		IteratorHelper.assertSameElements(a.getStates(), 
				"S");
		IteratorHelper.assertSameElements(a.getFinalStates(),
				"S");
		IteratorHelper.assertSameElements(a.getTransitions("S"), 
				new Transition("S", Terminal.LAMBDA, "S"));
	}
	
	@Test
	public void test2() {
		Grammar gr = IO.parseGr("<(S,A,B),(a,b),((S,a,A)(A,LAMBDA)(A,b,B)(B,b,B)(B,LAMBDA)),S>");
		AF a = GrToAutomata.convert(gr);

		IteratorHelper.assertSameElements(a.getAlphabet(),
				"a", "b");

		assertEquals("S", a.getInitialState());
		IteratorHelper.assertSameElements(a.getStates(), 
				"S", "A", "B");
		IteratorHelper.assertSameElements(a.getFinalStates(),
				"A", "B");
		IteratorHelper.assertSameElements(a.getTransitions("S"), 
				new Transition("S", "a", "A"));
		IteratorHelper.assertSameElements(a.getTransitions("A"), 
				new Transition("A", "b", "B"),
				new Transition("A", Terminal.LAMBDA, "A"));
		IteratorHelper.assertSameElements(a.getTransitions("B"), 
				new Transition("B", "b", "B"),
				new Transition("B", Terminal.LAMBDA, "B"));
	}

	@Test
	public void test3() {
		Grammar gr = IO.parseGr("<(A),(1),((A,1)),A>");
		AF a = GrToAutomata.convert(gr);

		IteratorHelper.assertSameElements(a.getAlphabet(), "1");
		
		assertEquals("A", a.getInitialState());
		assertNotNull(GrToAutomata.getLastDeadState());
		IteratorHelper.assertSameElements(a.getStates(), 
				"A", "ZA");
		IteratorHelper.assertSameElements(a.getFinalStates(),
				GrToAutomata.getLastDeadState());
		
		IteratorHelper.assertSameElements(a.getTransitions("A"), 
				new Transition("A", "1", GrToAutomata.getLastDeadState()));
		IteratorHelper.assertSameElements(
				a.getTransitions(GrToAutomata.getLastDeadState()));
		
	}
}
