package noam;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GrConverterFixture extends BaseFormalismConverterFixture {
	private GrConverter c(String input) {
		GrConverter c = new GrConverter(input);
		assertTrue(c.isFormalismOk());
		return c;
	}
	
	@Test
	public void testLambda(){
		assertCanConvert(c("<(A),(),((A,LAMBDA)),A>"));
	}
	
	@Test
	public void testOddBinary() {
		assertCanConvert(c("<(S,A),(0,1),((S,1)(S,0,S)(S,1,A)(A,1)(A,0,S)(A,1,A)),S>"));
	}
	
	@Test
	public void testA() {
		assertCanConvert(c("<(S),(1),((S,LAMBDA)(S,1,S)),S>"));
	}
}
