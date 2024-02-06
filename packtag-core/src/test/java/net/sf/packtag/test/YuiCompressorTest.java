/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.test;

import net.sf.packtag.implementation.yui.YuiCompressorPackStrategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test for the YUICompressor.
 * It isn't testable, because the literatls change their name on each run.
 *
 * @author Daniel Galán y Martins
 */
public class YuiCompressorTest extends AbstractPacktagTestCase {

	@Test
	public void testYui() throws Exception {
		YuiCompressorPackStrategy strategy = new YuiCompressorPackStrategy();
		strategy.pack(YuiCompressorTest.class.getResourceAsStream("js01.js"), getDefaultCharset(), null);
		//String expectedResult = resourceToString(JsminPackStrategyTest.class.getResourceAsStream("js01-yui.js"));
		//assertEquals(expectedResult, result);
	}

}
