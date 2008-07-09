package noam.er.grammar;

import static org.junit.Assert.assertEquals;
import noam.IO;
import noam.er.ER;
import noam.er.ERChoice;
import noam.er.ERClosure;
import noam.er.ERConcat;
import noam.er.EREmpty;
import noam.er.ERLambda;
import noam.er.ERTerminal;

import org.junit.Test;


public class ERGrammarFixture {

	@Test
	public void parseEmpty() {
		assertEquals(new EREmpty(), IO.parseER("VACIO"));
	}

	@Test
	public void parseLambda() {
		assertEquals(new ERLambda(), IO.parseER("LAMBDA"));
	}

	@Test
	public void parseTerminal(){
		assertEquals(t("a"), IO.parseER("a"));
	}

	@Test
	public void parseConcat(){
		assertEquals(c(t("a"),t("b")), IO.parseER("a.b"));
	}

	@Test
	public void parseChoice(){
		assertEquals(o(t("a"),t("b")), IO.parseER("a|b"));
	}

	@Test
	public void parseClosure(){
		assertEquals(k(t("c")), IO.parseER("c*"));
	}
	
	@Test
	public void closureLowestPrecedence(){
		assertEquals(o(t("c"),k(t("d"))), IO.parseER("c|d*"));
		assertEquals(c(t("c"),k(t("d"))), IO.parseER("c.d*"));
	}
	
	@Test
	public void concatLowerThanChoice(){
		assertEquals(o(c(t("a"),t("b")), c(t("c"),t("d"))), IO.parseER("a.b|c.d"));
	}

	@Test
	public void parensOverConcatAndChoice(){
		assertEquals(c(t("a"),c(o(t("b"), t("c")),t("d"))), IO.parseER("a.(b|c).d"));
	}

	@Test
	public void concatAssoc(){
		assertEquals(c(t("a"),c(t("b"),c(t("c"),t("d")))), IO.parseER("a.b.c.d"));
	}

	@Test
	public void concatAssocModifiedByParens(){
		assertEquals(c(c(c(t("a"),t("b")),t("c")),t("d")), IO.parseER("(((a.b).c).d)"));
	}

	@Test
	public void choiceAssoc(){
		assertEquals(o(t("a"),o(t("b"),o(t("c"),t("d")))), IO.parseER("a|b|c|d"));
	}

	@Test
	public void closureAssoc(){
		assertEquals(k(k(k(t("a")))), IO.parseER("a***"));
	}

	private static ER t(String a) {
		return new ERTerminal(a);
	}
	
	private static ER c(ER a, ER b) {
		return new ERConcat(a, b);
	}

	private static ER o(ER a, ER b) {
		return new ERChoice(a, b);
	}

	private static ER k(ER a) {
		return new ERClosure(a);
	}
}
