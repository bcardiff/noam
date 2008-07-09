package noam.af.algorithms;

import static noam.utils.IteratorHelper.*;
import static org.junit.Assert.*;
import noam.IO;
import noam.af.AF;
import noam.af.Transition;

import org.junit.Test;


public class AFUnionFixture {

	@Test
	public void disjointUnion(){
		AF a = IO.parseAF("<(A),(),(),A,(A)>");
		AF b = IO.parseAF("<(B),(),(),B,(B)>");
		AFUnion u = new AFUnion(a,b);
		assertSameElements(u.getStates(), "A", "B");
	}

	@Test
	public void nonDisjointUnion(){
		AF a = IO.parseAF("<(A,Z,X),(a,b),((A,a,Z)(Z,a,X)),A,(Z)>");
		AF b = IO.parseAF("<(B,Z,X),(a,b),((B,a,Z)(Z,b,X)),B,(Z)>");
		AFUnion u = new AFUnion(a,b);
		assertFalse(u.getFinalStates().hasNext());
		assertSameElements(u.getStates(), "A", "B", "Z", "X");
		assertSameElements(u.getTransitions("A"), new Transition("A","a","Z"));
		assertSameElements(u.getTransitions("B"), new Transition("B","a","Z"));
		assertSameElements(u.getTransitions("Z"), new Transition("Z","a","X"), new Transition("Z","b","X"));
		assertFalse(u.getTransitions("X").hasNext());
	}
}
