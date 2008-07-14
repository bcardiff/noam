package noam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ErConverterFixture {

	private ErConverter c(String input) {
		ErConverter c = new ErConverter(input);
		assertTrue(c.isFormalismOk());
		return c;
	}

	private void assertCanConvert(ErConverter c) {
		c.toAF();
		c.toAFD();
		c.toAFM();
		c.toER();
		c.toGR();
	}

	@Test
	public void testVacio() {
		ErConverter c = c("VACIO");
		assertEquals("<(A,B),(),(),A,(B)>", IO.print(c.toAF()));
		assertCanConvert(c);
	}

	@Test
	public void testLambda() {
		ErConverter c = c("LAMBDA");
		assertEquals("<(A,B),(),((A,LAMBDA,B)),A,(B)>", IO.print(c.toAF()));
		assertCanConvert(c);
	}

	@Test
	public void testTerminal() {
		ErConverter c = c("a");
		assertEquals("<(A,B),(a),((A,a,B)),A,(B)>", IO.print(c.toAF()));
		assertEquals("<(A,B),(a),((A,a,B)),A,(B)>", IO.print(c.toAFD()));
		assertCanConvert(c);
	}

	@Test
	public void testConcat() {
		ErConverter c = c("a.b");
		assertEquals("<(A,B,C,D),(a,b),((A,a,B)(B,LAMBDA,D)(D,b,C)),A,(C)>", IO
				.print(c.toAF()));
		assertCanConvert(c);
	}

	@Test
	public void testChoice() {
		ErConverter c = c("a|b");
		assertEquals(
				"<(A,B,C,D,E,F),(a,b),((A,a,D)(B,LAMBDA,A)(B,LAMBDA,C)(C,b,E)(D,LAMBDA,F)(E,LAMBDA,F)),B,(F)>",
				IO.print(c.toAF()));
		assertCanConvert(c);
	}

	@Test
	public void testClosure() {
		ErConverter c = c("a*");
		assertEquals("<(A,B),(a),((A,a,B)(B,LAMBDA,A)),A,(A)>", IO.print(c
				.toAF()));
		assertCanConvert(c);
	}

	@Test
	public void testDoubleClosure() {
		ErConverter c = c("a**");
		assertEquals("<(A,B),(a),((A,LAMBDA,A)(A,a,B)(B,LAMBDA,A)),A,(A)>", IO
				.print(c.toAF()));
		assertCanConvert(c);
	}

	@Test
	public void testTripleClosure() {
		ErConverter c = c("a***");
		assertEquals("<(A,B),(a),((A,LAMBDA,A)(A,a,B)(B,LAMBDA,A)),A,(A)>", IO
				.print(c.toAF()));
		assertCanConvert(c);
	}
}
