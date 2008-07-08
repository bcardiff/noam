package noam.af.algorithms;

import static org.junit.Assert.*;
import static noam.utils.IteratorHelper.assertSameElements;

import java.io.Console;
import java.util.Iterator;

import noam.IO;
import noam.af.AF;
import noam.af.Transition;

import org.junit.Test;


public class DeterminationFixture {

	@Test
	public void DFAShouldOnlyBeRenamed(){
		AF a = IO.parseAF("<(I,F),(a),((I,a,F)),I,(F)>");
		AF b = new Determination(a);
		
		String initialState = b.getInitialState();
		
		// one final state
		Iterator<String> finalStates =b.getFinalStates(); 
		assertTrue(finalStates.hasNext());
		String finalState = finalStates.next();
		assertFalse(finalStates.hasNext());
		
		// one transition
		Iterator<Transition> transitions = b.getTransitions(initialState);
		assertTrue(transitions.hasNext());
		Transition t = transitions.next();		
		assertFalse(transitions.hasNext());
		
		assertEquals(initialState, t.getFrom());
		assertEquals("a", t.getLabel());
		assertEquals(finalState, t.getTo());
		
		System.out.append(IO.printDot(b));
	}

	@Test
	public void test1(){
		AF a = IO.parseAF("<(I,F),(a),((I,a,F)(I,LAMBDA,F)),I,(F)>");
		AF b = new Determination(a);
		
		assertEquals("A", b.getInitialState());
		assertSameElements(b.getFinalStates(), "A","B");
		
		// one transition
		Iterator<Transition> transitions = b.getTransitions("A");
		assertTrue(transitions.hasNext());
		Transition t = transitions.next();		
		assertFalse(transitions.hasNext());
		
		assertEquals("A", t.getFrom());
		assertEquals("a", t.getLabel());
		assertEquals("B", t.getTo());
				
		System.out.append(IO.printDot(a));
		System.out.append(IO.printDot(b));
	}
	
	@Test
	public void Hopcroft2_18(){
		AF a = IO.parseAF("<(A,B,C,D,E,F),(p,m,d,0,1,2),((A,LAMBDA,B)(A,p,B)(A,m,B)" + dig("B","B")+dig("B","E")+"(B,d,C)"+dig("C","D")+dig("D","D") + "(E,d,D)(D,LAMBDA,F)),A,(F)>");
		System.out.append(IO.printDot(a));
		System.out.append(IO.printDot(new Determination(a)));
	}

	private String dig(String from, String to) {
		return "(" + from + ",0," + to + ")(" + from + ",1," + to + ")(" + from + ",2," + to + ")";
	}
}
