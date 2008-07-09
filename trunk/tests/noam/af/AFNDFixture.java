package noam.af;

import static noam.utils.IteratorHelper.*;
import static org.junit.Assert.*;

import java.util.Iterator;

import noam.IO;
import noam.af.algorithms.Complete;

import org.junit.Test;

public class AFNDFixture {

	@Test
	public void transitionByLabelCouldReturnEmpty() {
		AFND a = new AFND();
		a.setInitialState("A");
		a.addNode("B");
		a.addTransition("A", "x", "B");

		Iterator<Transition> it = a.getTransitions("B", "x");
		assertFalse(it.hasNext());

		Iterator<Transition> it2 = a.getTransitions("B");
		assertFalse(it2.hasNext());
	}
	
	@Test
	public void transitionReturnsEmtpy(){
		AF a = IO.parseAF("<(A,B,C),(x,y),((A,x,B)(B,y,C)),A,(C)>");
	
		assertSameElements(a.getTransitions("C"));
	}
}
