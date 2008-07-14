package noam.af.algorithms;

import static org.junit.Assert.assertEquals;
import noam.IO;
import noam.af.AF;
import noam.er.ER;

import org.junit.Test;

public class AFDtoERFixture {
	
	@Test
	public void test1(){
		AF ab = IO.parseAF("<(A,B),(a,b),((A,a,B)(A,b,B)(B,a,B)(B,b,A)),A,(B)>");
		AF a = ab;
		//ER er = IO.parseER("(a|b).(b.a|b.b|a)*");		
		ER er = IO.parseER("((b|a|b|a).(((b.(b|a))|a|LAMBDA)*).((b.(b|a))|a|LAMBDA))|b|a|b|a");
		ER traducida= (new AFDtoER(a)).convert();		
		assertEquals(er.toString(), traducida.toString());
	}
	
	@Test
	public void test2() throws Exception {
		AF a = IO.parseAF("<(A),(a),((A,a,A)),A,(A)>");
		ER exp = IO.parseER("((a|LAMBDA).(a|LAMBDA)*.(a|LAMBDA))|(a|LAMBDA)");
		ER act = (new AFDtoER(a)).convert();
		assertEquals(exp.toString(), act.toString());
	}
	
}
