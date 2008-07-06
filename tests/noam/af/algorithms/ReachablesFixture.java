package noam.af.algorithms;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static noam.utils.IteratorHelper.contains;
import noam.IO;
import noam.af.AF;
import noam.af.InvalidStateException;

import org.junit.Test;


public class ReachablesFixture {

	@Test
	public void testDirectlyNoReachablesAreRemoved(){
		AF a1 = IO.parseAF("<(I,F),(),(),I,(I)>");
		AF a2 = new Reachables(a1);
		assertTrue(contains(a2.getStates(), "I"));
		assertFalse(contains(a2.getStates(), "F"));
	}

	@Test
	public void testDirectlyReachableAreInlcuded(){
		AF a1 = IO.parseAF("<(I,A,F),(x),((I,x,A)),I,(I)>");
		AF a2 = new Reachables(a1);
		assertTrue(contains(a2.getStates(), "I"));
		assertTrue(contains(a2.getStates(), "A"));
		assertFalse(contains(a2.getStates(), "F"));
	}

	@Test
	public void testOnlyReachablesAreInlcuded(){
		AF a1 = IO.parseAF("<(I,A,B,C,D,E,F,G),(x),((I,x,A) (A,x,B) (B,x,C) (B,x,D) (C,x,E) (F,x,G)),I,(E,G)>");
		AF a2 = new Reachables(a1);
		assertTrue(contains(a2.getStates(), "I"));
		assertTrue(contains(a2.getStates(), "A"));
		assertTrue(contains(a2.getStates(), "B"));
		assertTrue(contains(a2.getStates(), "C"));
		assertTrue(contains(a2.getStates(), "D"));
		assertTrue(contains(a2.getStates(), "E"));
		assertFalse(contains(a2.getStates(), "F"));
		assertFalse(contains(a2.getStates(), "G"));
	}
	
	@Test
	public void testOnlyReachablesCanBeFinalStates(){	
		AF a1 = IO.parseAF("<(I,A,B,C,D,E,F,G),(x),((I,x,A) (A,x,B) (B,x,C) (B,x,D) (C,x,E) (F,x,G)),I,(E,G)>");
		AF a2 = new Reachables(a1);

		assertTrue(contains(a2.getFinalStates(),"E"));
		assertFalse(contains(a2.getFinalStates(),"G"));
	}
	
	@Test(expected=InvalidStateException.class)
	public void throwsWhenQueryNonReachableState() {
		AF a1 = IO.parseAF("<(I,F),(),(),I,(I)>");
		AF a2 = new Reachables(a1);
		
		a2.getTransitions("F");
	}	
}
