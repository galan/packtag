package net.sf.packtag.test;
import java.nio.charset.Charset;

import junit.framework.TestCase;



public class Charset2Test extends TestCase {

	private static final String LATIN_9 = "ISO-8859-15";

	private static final String UTF8 = "ISO-8859-15";


	public void testCharsetConversation() throws Exception {
		//wrong test case input text is right encoding.
		String text = new String("Hello World ??".getBytes(UTF8), UTF8);
		byte[] bytesGet = text.getBytes(LATIN_9);

		Charset cs = Charset.forName(LATIN_9);
		byte[] bytesArray = cs.encode(text).array();

		byte[] bytesFix = new String(text.getBytes("ISO8859_1"), cs.name()).getBytes();
		// This testcase will return allways the same results:
		// [72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100, 32, -33,
		// -28,-10, -4]
		// System.out.println("b1: " + bytesGet);
		// System.out.println("b2: " + bytesArray);
		// System.out.println("b3: " + bytesFix);

		assertEquals(bytesGet, bytesArray);
		assertEquals(bytesGet, bytesFix);
	}


	public void testCharsetConversation2() throws Exception {
		String wrongInputText = new String("Hello World ??".getBytes("UTF-8"), "ISO8859_1");
		System.out.println("wrong input encoding:" + wrongInputText);
		//inputsream encoding is "ISO8859_1"
		String text = new String(wrongInputText.getBytes(UTF8), UTF8);
		byte[] bytesGet = text.getBytes(LATIN_9);

		Charset cs = Charset.forName(LATIN_9);
		byte[] bytesArray = cs.encode(text).array();

		byte[] bytesFix = new String(text.getBytes("ISO8859_1"), cs.name()).getBytes();
		// This testcase will return allways the same results:
		// [72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100, 32, -33,
		// -28,-10, -4]
		// System.out.println("b1: " + bytesGet);
		// System.out.println("b2: " + bytesArray);
		// System.out.println("b3: " + bytesFix);

		assertEquals(bytesGet, bytesArray);
		assertEquals(bytesGet, bytesFix);
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
