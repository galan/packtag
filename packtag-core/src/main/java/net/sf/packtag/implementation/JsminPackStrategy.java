/* Project pack:tag >> https://github.com/galan/packtag */
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
 * @author Daniel Galán y Martins
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
