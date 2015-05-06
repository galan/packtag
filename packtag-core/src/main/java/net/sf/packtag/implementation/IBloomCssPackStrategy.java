/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.implementation;

import java.io.InputStream;
import java.nio.charset.Charset;

import net.sf.packtag.strategy.CssRewritePackStrategy;
import net.sf.packtag.strategy.PackException;



/**
 * Strategy to compress CSS files.<br/> See <a
 * href="http://www.ibloomstudios.com/articles/php_css_compressor/">here</a>
 * for more information.
 *
 * @author Daniel Galán y Martins
 */
public class IBloomCssPackStrategy extends CssRewritePackStrategy {

	private static final String IBLOOM_REGEX_COMMENTS = "/\\*[^*]*\\*+([^/][^*]*\\*+)*/";
	private static final String IBLOOM_REGEX_CONTROL = "(\r\n)|(\r)|(\n)|(\t)";
	private static final String REGEX_SPACES = "(  +)";
	private static final String EMPTY_STRING = "";
	private static final String SINGLE_SPACE = " ";


	/**
	 * Packs a CSS resource while replacing every occurring background image
	 * path to an absolute one (unless it isn't already an absolute one). This
	 * ensures working images when re-mapping the base-path to a different root
	 * under the web application.
	 */
	public String pack(final InputStream resourceAsStream, final Charset charset, final String path) throws PackException {
		String resourceAsString = resourceToString(resourceAsStream, charset);
		// remove comments
		String result = resourceAsString.replaceAll(IBLOOM_REGEX_COMMENTS, EMPTY_STRING);
		// remove tabs, spaces, newlines, etc.
		result = result.replaceAll(IBLOOM_REGEX_CONTROL, EMPTY_STRING);
		result = result.replaceAll(REGEX_SPACES, SINGLE_SPACE);
		result = rewritePath(result, path);
		return result.trim();
	}

}
