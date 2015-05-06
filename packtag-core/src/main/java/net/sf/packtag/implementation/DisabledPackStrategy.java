/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.implementation;

import java.io.InputStream;
import java.nio.charset.Charset;

import net.sf.packtag.strategy.CssRewritePackStrategy;
import net.sf.packtag.strategy.PackException;



/**
 * A Strategy that does not do any compression, instead it's simply returning
 * the files content
 *
 * @author Daniel Galán y Martins
 */
public class DisabledPackStrategy extends CssRewritePackStrategy {

	/**
	 * Just replacing every occurring background image path to an absolute one
	 * (unless it isn't already an absolute one). This ensures working images
	 * when re-mapping the base-path to a different root under the web
	 * application.
	 */
	public String pack(final InputStream resourceAsStream, final Charset charset, final String path) throws PackException {
		String result = resourceToString(resourceAsStream, charset);
		result = rewritePath(result, path);
		return result;
	}

}
