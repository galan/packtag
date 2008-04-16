/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 12.10.2007 - 23:35:53
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 16:30:10 $
 * Revision:      $Revision: 1.5 $
 * 
 * $Log: CharsetUtil.java,v $
 * Revision 1.5  2008/03/15 16:30:10  danielgalan
 * Field definitions could be used anywhere
 *
 * Revision 1.4  2008/02/10 20:59:39  danielgalan
 * static used fields
 *
 * Revision 1.3  2007/11/06 23:12:04  danielgalan
 * Update Author 3
 *
 * Revision 1.2  2007/10/31 09:01:57  danielgalan
 * javadoc
 *
 * Revision 1.1  2007/10/12 21:50:40  danielgalan
 * default Charset solution
 *
 */
package net.sf.packtag.util;

import java.lang.reflect.Method;
import java.nio.charset.Charset;



/**
 * Helps on Charset relevant operations
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.5 $
 */
public class CharsetUtil {

	public static final String LATIN_9 = "ISO-8859-15";
	public static final String UTF8 = "UTF-8";


	/** Returns the platform default Charset (on >Java5, elswise Latin9 is assumped). */
	public Charset getDefaultCharset() {
		try {
			// Try to get the real plattform default Charset, thru reflection of 1.5 Methods
			Method methodDefaultCharset = Charset.class.getMethod("defaultCharset", new Class[] {});
			if (methodDefaultCharset != null) {
				return (Charset)methodDefaultCharset.invoke(null, new Object[] {});
			}
		}
		catch (Exception e) {
			// In this case, we'll take the Java 1.4 solution
		}

		// ohoh .. seems to be Java 1.4, we take a default charset now
		// because you can't identify the JVM default charset
		String charsetName = Charset.isSupported(LATIN_9) ? LATIN_9 : UTF8;
		return Charset.forName(charsetName);
	}

}
