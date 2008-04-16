/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 30.01.2008 - 23:37:22
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 21:36:57 $
 * Revision:      $Revision: 1.2 $
 * 
 * $Log: BooleanStringTest.java,v $
 * Revision 1.2  2008/03/15 21:36:57  danielgalan
 * Name correction.
 *
 * Revision 1.1  2008/02/10 21:18:05  danielgalan
 * simple String/Boolean Test
 *
 */
package net.sf.packtag.test;

import junit.framework.TestCase;



/**
 * Just a stupid test to evaluate Boolean Strings
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.2 $
 */
public class BooleanStringTest extends TestCase {

	public void testBooleanString() {
		assertEquals("true", Boolean.TRUE.toString());
		assertEquals("false", Boolean.FALSE.toString());
	}

}
