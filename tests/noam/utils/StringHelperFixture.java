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
}
