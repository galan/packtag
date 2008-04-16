/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 15.08.2007 - 23:13:13
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2007/09/23 14:37:31 $
 * Revision:      $Revision: 1.2 $
 * 
 * $Log: YuiCompressorTest.java,v $
 * Revision 1.2  2007/09/23 14:37:31  danielgalan
 * Added charset support
 *
 * Revision 1.1  2007/08/27 22:38:09  danielgalan
 * YuiCompressor
 *
 */
package net.sf.packtag.test;

import net.sf.packtag.implementation.yui.YuiCompressorPackStrategy;



/**
 * Test for the YUICompressor.
 * It isn't testable, because the literatls change their name on each run.
 * 
 * @author danielgalan
 * @version $Revision: 1.2 $
 */
public class YuiCompressorTest extends AbstractPacktagTestCase {

	public void testYui() throws Exception {
		YuiCompressorPackStrategy strategy = new YuiCompressorPackStrategy();
		strategy.pack(YuiCompressorTest.class.getResourceAsStream("js01.js"), getDefaultCharset());
		//String expectedResult = resourceToString(JsminPackStrategyTest.class.getResourceAsStream("js01-yui.js"));
		//assertEquals(expectedResult, result);
	}

}
