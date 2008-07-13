package noam.gr.algorithms;

import static junit.framework.Assert.assertEquals;
import noam.IO;
import noam.gr.Grammar;
import noam.gr.Production;
import noam.gr.algorithms.Normalization;
import noam.utils.IteratorHelper;

import org.junit.Test;

public class NormalizationFixture {
	
	@Test
	public void test1() {
		Grammar gr = IO.parseGr("<(A,S),(1,0),((A,LAMBDA)(A,1,A)(S,0,A)),S>");
		Normalization.normalize(gr);

		IteratorHelper.assertSameElements(gr.getProductions(), 
				new Production("S", "0"),
				new Production("A", "1"),
				new Production("A", "1", "A"),
				new Production("S", "0", "A"));
		assertEquals("S", gr.getDistSymbol());
		IteratorHelper.assertSameElements(gr.getNonTerminals(), 
				"S", "A");
		IteratorHelper.assertSameElements(gr.getTerminals(), 
				"0", "1");
	}
	
	@Test
	public void test2() {
		Grammar gr = IO.parseGr("<(A,S),(1,0),((A,LAMBDA)(A,1,A)(S,LAMBDA)(S,0,A)),S>");
		Normalization.normalize(gr);
		
		IteratorHelper.assertSameElements(gr.getProductions(),
				new Production("S"),
				new Production("S", "0"),
				new Production("A", "1"),
				new Production("A", "1", "A"),
				new Production("S", "0", "A"));
		assertEquals("S", gr.getDistSymbol());
		IteratorHelper.assertSameElements(gr.getNonTerminals(), 
				"S", "A");
		IteratorHelper.assertSameElements(gr.getTerminals(), 
				"0", "1");	
	}
}
