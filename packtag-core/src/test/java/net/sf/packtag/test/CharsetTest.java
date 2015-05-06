/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.test;

import java.nio.charset.Charset;

import junit.framework.TestCase;



/**
 * Tests some issues with Charset, etc..
 *
 * @author  Daniel Galán y Martins
 */
public class CharsetTest extends TestCase {

	private static final String LATIN_9 = "ISO-8859-15";
	private static final String UTF8 = "ISO-8859-15";


	public void testCharsetConversation() throws Exception {
		String text = new String("Hello World �����".getBytes(UTF8), UTF8);
		byte[] bytesGet = text.getBytes(LATIN_9);

		Charset cs = Charset.forName(LATIN_9);
		byte[] bytesArray = cs.encode(text).array();

		//byte[] bytesFixWithLatin1 = new String(text.getBytes("ISO8859_1"), cs.name()).getBytes();
		// This testcase will return allways the same results:
		// [72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100, 32, -33, -28, -10, -4]
		//System.out.println("b1: " + bytesGet);
		//System.out.println("b2: " + bytesArray);
		//System.out.println("b3: " + bytesFix);

		assertEquals(bytesGet, bytesArray);
		// will fail assertEquals(bytesGet, bytesFixWithLatin1);
	}


	public void assertEquals(final byte[] array, final byte[] expected) {
		if (array.length != expected.length) {
			fail("Different bytearray size");
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i] != expected[i]) {
				fail("Different content in bytearray at position " + i);
			}
		}
	}

}
