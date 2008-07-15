package noam;

import static org.junit.Assert.assertTrue;

import noam.af.AF;

import org.junit.Test;

public class AfFormalismConverterFixture extends BaseFormalismConverterFixture {

	private AfConverter c(String input) {
		AfConverter c = new AfConverter(input);
		assertTrue(c.isFormalismOk());
		return c;
	}

	@Test
	public void testLambda(){
		assertCanConvert(c("<(A),(),(),A,(A)>"));
	}

	@Test
	public void testLambda2(){
		assertCanConvert(c("<(A,B),(),((A,LAMBDA,B)),A,(B)>"));
	}

	@Test
	public void testLambda3(){
		assertCanConvert(c("<(A,B),(),((A,LAMBDA,B)),A,(A,B)>"));
	}

	@Test
	public void testVacio(){
		assertCanConvert(c("<(A,B),(),(),A,(B)>"));
	}
	
	@Test
	public void testTerminal(){
		assertCanConvert(c("<(A,B),(a),((A,a,B)),A,(B)>"));
	}

	@Test
	public void testChoice(){
		assertCanConvert(c("<(A,B),(a,b),((A,a,B)(A,b,B)),A,(B)>"));
	}

	@Test
	public void test1oMas(){
		assertCanConvert(c("<(A,B),(a),((A,a,B)(B,a,B)),A,(B)>"));
	}

	@Test
	public void testABEstrella(){
		assertCanConvert(c("<(A,B),(a,b),((A,a,B)(B,b,B)),A,(B)>"));
	}
	
	@Test
	public void testDecimalNumbers_Hopcroft2_18(){
		assertCanConvert(c("<(A,B,C,D,E,F),(p,m,d,0,1,2),((A,LAMBDA,B)(A,p,B)(A,m,B)" + dig("B","B")+dig("B","E")+"(B,d,C)"+dig("C","D")+dig("D","D") + "(E,d,D)(D,LAMBDA,F)),A,(F)>"));
	}

	private String dig(String from, String to) {
		return "(" + from + ",0," + to + ")(" + from + ",1," + to + ")(" + from + ",2," + to + ")";
	}
}
