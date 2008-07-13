package noam.er;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class ERFixture {
	
	@Test
	public void toStringTest(){
		assertEquals("a", t("a").toString());
		assertEquals("a.b", c(t("a"),t("b")).toString());
		assertEquals("a|b", o(t("a"),t("b")).toString());
		assertEquals("c*", k(t("c")).toString());
		assertEquals("a.b.c", c(t("a"),c(t("b"),t("c"))).toString());
		assertEquals("a.b.c", c(c(t("a"),t("b")),t("c")).toString());
		assertEquals("a.(b|c)", c(t("a"),o(t("b"),t("c"))).toString());
		assertEquals("(a|b).c", c(o(t("a"),t("b")),t("c")).toString());
		assertEquals("(a|(b.(d**))).c", c(o(t("a"),c(t("b"),k(k(t("d"))))),t("c")).toString());
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
