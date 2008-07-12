package noam.af.algorithms;

import static noam.utils.IteratorHelper.assertSameElements;
import static org.junit.Assert.assertEquals;

import java.io.Console;

import noam.IO;
import noam.af.AF;
import noam.af.Transition;
import noam.utils.StringHelper;

import org.junit.Test;


public class AFRenamedFixture {

	@Test
	public void testRename(){
		AF a = IO.parseAF("<(A,B),(a),((A,a,B)),A,(B)>");
		AF b = new AFRenamed(a, StringHelper.addPrefix("Q"), StringHelper.removePrefix("Q"));
		assertSameElements(b.getStates(), "QA", "QB");
		assertSameElements(b.getFinalStates(), "QB");
		assertEquals(b.getInitialState(), "QA");
		assertSameElements(b.getTransitions("QA"), new Transition("QA","a","QB"));
	}

	@Test
	public void testCanonicalName(){
		AF a = IO.parseAF("<(X,Y,Z),(a,b,c),((X,a,Y)(Y,b,Z)(Z,c,X)),X,(Z)>");
		AF b = AFRenamed.CanonicalNamed(a);
		assertSameElements(b.getStates(), "A", "B", "C");
		System.out.append(IO.printDot(b));
	}
}
