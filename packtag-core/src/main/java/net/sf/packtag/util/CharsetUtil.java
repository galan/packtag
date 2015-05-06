/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.util;

import java.lang.reflect.Method;
import java.nio.charset.Charset;



/**
 * Helps on Charset relevant operations
 *
 * @author  Daniel Galán y Martins
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
