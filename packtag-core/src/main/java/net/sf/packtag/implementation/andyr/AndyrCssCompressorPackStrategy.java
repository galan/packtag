/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.implementation.andyr;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import net.sf.packtag.strategy.CssRewritePackStrategy;
import net.sf.packtag.strategy.PackException;



/**
 * Wraps the CssCompressor by Andrew Roberts, published under BSD.
 * http://www.andy-roberts.net/software/csscompressor
 *
 * @author Daniel Galán y Martins
 */
public class AndyrCssCompressorPackStrategy extends CssRewritePackStrategy {

	private static final int LINEBREAK_AFTER_CHARACTERS = 8000;


	public String pack(final InputStream resourceAsStream, final Charset charset, final String path) throws PackException {
		String result = resourceToString(resourceAsStream, charset);
		result = rewritePath(result, path);
		try {
			AndryCssCompressor compressor = new AndryCssCompressor(result);
			StringWriter sw = new StringWriter();
			compressor.compress(sw, LINEBREAK_AFTER_CHARACTERS);
			sw.flush();
			result = sw.toString();
		}
		catch (IOException ex) {
			throw new PackException(ex);
		}
		return result;
	}

}
