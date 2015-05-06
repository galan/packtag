/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.implementation.jawr;

import java.io.InputStream;
import java.nio.charset.Charset;

import net.jawr.web.minification.CSSMinifier;
import net.sf.packtag.strategy.CssRewritePackStrategy;
import net.sf.packtag.strategy.PackException;



/**
 * Wraps the CSSMinifier by Jordi Hern�ndez Sell�s, published under the Apache Licence 2.0.
 * See the licence.txt for more information about the Apache Licence, or go to his website:
 * https://jawr.dev.java.net/
 *
 * @author Daniel Galán y Martins
 */
public class CssMinifierPackStrategy extends CssRewritePackStrategy {

	/**
	 * Packs a CSS resource while replacing every occurring background image
	 * path to an absolute one (unless it isn't already an absolute one). This
	 * ensures working images when re-mapping the base-path to a different root
	 * under the web application.
	 */
	public String pack(final InputStream resourceAsStream, final Charset charset, final String path) throws PackException {
		CSSMinifier minifier = new CSSMinifier();
		StringBuffer resource = new StringBuffer(resourceToString(resourceAsStream, charset));
		String result = minifier.minifyCSS(resource).toString();
		result = rewritePath(result, path);
		return result.toString();
	}

}
