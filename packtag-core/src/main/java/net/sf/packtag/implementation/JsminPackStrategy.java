/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL License version 2.1, a
 * copy of which has been included with this distribution in the 'lgpl.txt'
 * file.
 *
 * Last author: $Author: danielgalan $ Last modified: $Date: 2007/10/13 20:32:08 $
 * Revision: $Revision: 1.4 $
 *
 * $Log: JsminPackStrategy.java,v $ Revision 1.4 2007/10/13 20:32:08 danielgalan
 * Updated a minor behavior
 *
 * Revision 1.3 2007/10/13 20:19:41 danielgalan Moved JSMin away from
 * Inputstreams to Reader/Writer
 *
 * Revision 1.2 2007/09/23 14:37:31 danielgalan Added charset support
 *
 * Revision 1.1 2007/04/22 19:04:25 danielgalan pack.tag moved from subversion
 * to good old CVS
 *
 */
package net.sf.packtag.implementation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;

import net.sf.packtag.strategy.PackException;
import net.sf.packtag.strategy.PackStrategy;
import net.sf.packtag.util.SafeLogger;



/**
 * Strategy for compressing JavaScript resources with the JSMin algorithm, taken
 * from Douglas Crockford: http://www.crockford.com This Strategy does not
 * support charsets.
 *
 * @author Daniel Gal�n y Martins
 * @version $Revision: 1.4 $
 */
public class JsminPackStrategy implements PackStrategy {

	public String pack(final InputStream resourceAsStream, final Charset charset, final String path) throws PackException {
		InputStreamReader isr = new InputStreamReader(resourceAsStream, charset);
		BufferedReader br = new BufferedReader(isr);
		StringWriter sw = new StringWriter();
		JSMin min = new JSMin(br, sw);
		try {
			min.jsmin();
			return sw.toString().trim();
		}
		catch (Exception ex) {
			SafeLogger.error(getClass(), "Exception is " + ex.getClass(), ex);
			throw new PackException("Could not pack resource", ex);
		}
	}

}
