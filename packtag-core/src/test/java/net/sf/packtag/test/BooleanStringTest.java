/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.test;

import junit.framework.TestCase;



/**
 * Just a stupid test to evaluate Boolean Strings
 *
 * @author  Daniel Galán y Martins
 */
public class BooleanStringTest extends TestCase {

	public void testBooleanString() {
		assertEquals("true", Boolean.TRUE.toString());
		assertEquals("false", Boolean.FALSE.toString());
	}

}
