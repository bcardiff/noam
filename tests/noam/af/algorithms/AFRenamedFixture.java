package noam.af.algorithms;

import static noam.utils.IteratorHelper.assertSameElements;
import static org.junit.Assert.assertEquals;
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
}
