package noam.er.algorithms;

import noam.IO;
import noam.af.AF;
import noam.er.ER;
import noam.er.ERClosure;
import noam.er.ERTerminal;
import noam.utils.IteratorHelper;

import org.junit.Test;


public class ERToAutomataFixture {

	@Test
	public void test1(){
		//ER er = new ERConcat(new ERTerminal("a"), new ERTerminal("b"));
		//ER er = new ERClosure(new ERConcat(new ERTerminal("a"), new ERTerminal("b")));
		ER er = new ERClosure(new ERClosure(new ERClosure(new ERClosure(new ERTerminal("a")))));
		AF af = (AF) er.accept(new ERToAutomata());
		System.out.append(IO.printDot(af));
	}

	@Test
	public void alphabetCreated() {
		assertAlphapetOfAf(IO.parseER("a"),"a");
		assertAlphapetOfAf(IO.parseER("a.b"),"a","b");
		assertAlphapetOfAf(IO.parseER("a|b"),"a","b");
		assertAlphapetOfAf(IO.parseER("c*"),"c");
		assertAlphapetOfAf(IO.parseER("LAMBDA"));//empty alphabet
		assertAlphapetOfAf(IO.parseER("VACIO"));//empty alphabet
		assertAlphapetOfAf(IO.parseER("((a|b.c)|d*).VACIO"),"a","b","c","d");
	}

	private void assertAlphapetOfAf(ER er, String... symbols) {
		AF af = (AF) er.accept(new ERToAutomata());
		IteratorHelper.assertSameElements(af.getAlphabet(), symbols);		
	}
	
}
