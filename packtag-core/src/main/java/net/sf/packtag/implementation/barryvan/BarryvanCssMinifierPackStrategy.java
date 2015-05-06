/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.implementation.barryvan;

import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import net.sf.packtag.strategy.CssRewritePackStrategy;
import net.sf.packtag.strategy.PackException;



/**
 * Wraps the CSS minifier and alphabetiser by Barry van Oudtshoorn, published under MPL.
 * http://www.barryvan.com.au/2009/08/css-minifier-and-alphabetiser
 *
 * @author  Daniel Galán y Martins
 */
public class BarryvanCssMinifierPackStrategy extends CssRewritePackStrategy {

	public String pack(final InputStream resourceAsStream, final Charset charset, final String path) throws PackException {
		try {
			String result = resourceToString(resourceAsStream, charset);
			result = rewritePath(result, path);
			StringWriter sw = new StringWriter();
			new CSSMin().formatFile(result, sw);
			sw.flush();
			return sw.toString();
		}
		catch (Exception ex) {
			throw new PackException(ex);
		}
	}

}
