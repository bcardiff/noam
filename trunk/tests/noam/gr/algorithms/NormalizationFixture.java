package noam.gr.algorithms;

import static junit.framework.Assert.assertEquals;
import static noam.utils.IteratorHelper.assertSameElements;
import noam.IO;
import noam.gr.Grammar;
import noam.gr.Production;

import org.junit.Test;

public class NormalizationFixture {

	@Test
	public void test1() {
		Grammar gr = IO.parseGr("<(A,S),(1,0),((A,LAMBDA)(A,1,A)(S,0,A)),S>");
		Normalization.normalize(gr);

		assertSameElements(gr.getProductions(), new Production("S", "0"),
				new Production("A", "1"), new Production("A", "1", "A"),
				new Production("S", "0", "A"));
		assertEquals("S", gr.getDistSymbol());
		assertSameElements(gr.getNonTerminals(), "S", "A");
		assertSameElements(gr.getTerminals(), "0", "1");
	}

	@Test
	public void test2() {
		Grammar gr = IO
				.parseGr("<(A,S),(1,0),((A,LAMBDA)(A,1,A)(S,LAMBDA)(S,0,A)),S>");
		Normalization.normalize(gr);

		assertSameElements(gr.getProductions(), new Production("S"),
				new Production("S", "0"), new Production("A", "1"),
				new Production("A", "1", "A"), new Production("S", "0", "A"));
		assertEquals("S", gr.getDistSymbol());
		assertSameElements(gr.getNonTerminals(), "S", "A");
		assertSameElements(gr.getTerminals(), "0", "1");
	}

	@Test
	public void removeNonExpandableProductions() throws Exception {
		Grammar gr = IO.parseGr("<(A,B,C),(1,0),((A,1)(A,1,B)(A,1,C)),A>");
		Normalization.removeNonExpandableProductions(gr);
		assertSameElements(gr.getProductions(), new Production("A", "1"));
		assertSameElements(gr.getNonTerminals(), "A");
	}

	@Test
	public void removeNonExpandableProductionsExpectDistinguisehdSymbol() {
		Grammar gr = IO.parseGr("<(A,B),(1,0),((B,1,A)),A>");
		Normalization.removeNonExpandableProductions(gr);
		assertSameElements(gr.getProductions());
		assertSameElements(gr.getNonTerminals(), "A");
	}
}
