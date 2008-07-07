package noam.af;

import static org.junit.Assert.*;

import java.util.Iterator;

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
}
