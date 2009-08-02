/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2007/09/23 14:37:31 $
 * Revision:      $Revision: 1.2 $
 * 
 * $Log: JsminPackStrategyTest.java,v $
 * Revision 1.2  2007/09/23 14:37:31  danielgalan
 * Added charset support
 *
 * Revision 1.1  2007/04/22 19:04:23  danielgalan
 * pack.tag moved from subversion to good old CVS
 *
 */
package net.sf.packtag.test;

import net.sf.packtag.implementation.JsminPackStrategy;



/**
 * Test Case for the CSS algorithm taken from IBloom
 * 
 * @author Daniel Galán y Martins
 * @version $Revision: 1.2 $
 */
public class JsminPackStrategyTest extends AbstractPacktagTestCase {

	public void testCompressorOne() throws Exception {
		JsminPackStrategy strategy = new JsminPackStrategy();
		String result = strategy.pack(JsminPackStrategyTest.class.getResourceAsStream("js01.js"), getDefaultCharset(), null);
		String expectedResult = resourceToString(JsminPackStrategyTest.class.getResourceAsStream("js01-jsmin.js"));
		assertEquals(expectedResult, result);
	}

}
