package noam.er;

import java.awt.Choice;

import noam.IO;
import noam.af.AF;

import org.junit.Test;


public class ERToAutomataFixture {

	@Test
	public void test1(){
		//ER er = new ERConcat(new ERTerminal("a"), new ERTerminal("b"));
		ER er = new ERChoice(new ERConcat(new ERTerminal("a"), new ERTerminal("b")),new ERTerminal("c"));
		AF af = (AF) er.accept(new ERToAutomata());
		System.out.append(IO.printDot(af));
	}
}
