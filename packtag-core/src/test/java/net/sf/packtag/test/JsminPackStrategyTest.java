/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.test;

import net.sf.packtag.implementation.JsminPackStrategy;



/**
 * Test Case for the CSS algorithm taken from IBloom
 *
 * @author Daniel Galán y Martins
 */
public class JsminPackStrategyTest extends AbstractPacktagTestCase {

	public void testCompressorOne() throws Exception {
		JsminPackStrategy strategy = new JsminPackStrategy();
		String result = strategy.pack(JsminPackStrategyTest.class.getResourceAsStream("js01.js"), getDefaultCharset(), null);
		String expectedResult = resourceToString(JsminPackStrategyTest.class.getResourceAsStream("js01-jsmin.js"));
		assertEquals(expectedResult, result);
	}

}
