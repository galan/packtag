/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.implementation;

import net.sf.packtag.test.AbstractPacktagTestCase;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;



/**
 * Test Case for the CSS algorithm taken from IBloom
 *
 * @author Daniel Galán y Martins
 */
public class IBloomCssPackStrategyTest extends AbstractPacktagTestCase {

	@Test
	public void testCompressorOne() throws Exception {
		IBloomCssPackStrategy strategy = new IBloomCssPackStrategy();
		String result = strategy.pack(IBloomCssPackStrategyTest.class.getResourceAsStream("css01.css"), getDefaultCharset(), null);
		String expectedResult = resourceToString(IBloomCssPackStrategyTest.class.getResourceAsStream("css01-ibloom.css"));
		assertEquals(expectedResult, result);
	}

}
