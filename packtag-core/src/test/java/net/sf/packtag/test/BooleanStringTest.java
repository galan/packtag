/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Just a stupid test to evaluate Boolean Strings
 *
 * @author  Daniel Galán y Martins
 */
public class BooleanStringTest {

	@Test
	public void testBooleanString() {

		assertEquals("true", Boolean.TRUE.toString());
		assertEquals("false", Boolean.FALSE.toString());
	}

}
