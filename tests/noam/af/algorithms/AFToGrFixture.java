package noam.af.algorithms;

import static junit.framework.Assert.assertEquals;
import noam.IO;
import noam.af.AF;
import noam.gr.Grammar;
import noam.gr.Production;
import noam.utils.IteratorHelper;

import org.junit.Test;

public class AFToGrFixture {
	
	@Test
	public void test1() {
		AF a = IO.parseAF("<(A,B,C),(x,y),((A,x,B)(B,y,C)),A,(C)>");
		
		Grammar gr = AFToGr.convert(a);
		
		IteratorHelper.assertSameElements(gr.getProductions(), 
				new Production("A", "x", "B"),
				new Production("B", "y", "C"),
				new Production("C"));
		assertEquals("A", gr.getDistSymbol());
		IteratorHelper.assertSameElements(gr.getNonTerminals(), 
				"A", "B", "C");
		IteratorHelper.assertSameElements(gr.getTerminals(), 
				"x", "y");
	}
	
	@Test
	public void test2() {
		AF a = IO.parseAF("<(A),(1),((A,1,A)),A,(A)>");
		
		Grammar gr = AFToGr.convert(a);
		
		IteratorHelper.assertSameElements(gr.getProductions(), 
				new Production("A"),
				new Production("A", "1", "A"));
		assertEquals("A", gr.getDistSymbol());
		IteratorHelper.assertSameElements(gr.getNonTerminals(), 
				"A");
		IteratorHelper.assertSameElements(gr.getTerminals(), 
				"1");
	}
	
}
