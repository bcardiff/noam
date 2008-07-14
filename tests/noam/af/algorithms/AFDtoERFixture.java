package noam.af.algorithms;

import noam.IO;
import noam.af.AF;
import noam.af.Transition;
import noam.af.internal.AFNDBuilder;
import noam.er.ER;
import noam.er.ERChoice;
import noam.er.ERClosure;
import noam.er.ERConcat;
import noam.er.ERTerminal;
import noam.er.ERToAutomata;
import noam.er.grammar.ERParser;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AFDtoERFixture {

	
	//No anda. pero no se si esta bien el test
	@Test
	public void test1(){
		AF ab = IO.parseAF("<(A,B),(a,b),((A,a,B)(A,b,B)(B,a,B)(B,b,A)),A,(B)>");
		//AF a = new Determination(ab);
		AF a = ab;
		ER er = IO.parseER("(a|b).(b.a|b.b|a)*");		
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
