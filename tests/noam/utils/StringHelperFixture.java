package noam.utils;

import static org.junit.Assert.*;
import org.junit.Test;


public class StringHelperFixture {
	@Test
	public void testAddPrefix(){
		assertEquals("afoo", StringHelper.addPrefix("a").apply("foo"));
	}

	@Test
	public void testRemovePrefix(){
		assertEquals("foo", StringHelper.removePrefix("a").apply("afoo"));
	}
	
	@Test
	public void testAsString() throws Exception {
		assertEquals("A", StringHelper.asString(0));
		assertEquals("B", StringHelper.asString(1));
		assertEquals("Z", StringHelper.asString(25));
		assertEquals("AA", StringHelper.asString(26));
		assertEquals("AB", StringHelper.asString(27));
		assertEquals("AZ", StringHelper.asString(51));
		assertEquals("BA", StringHelper.asString(52));
	}
}
