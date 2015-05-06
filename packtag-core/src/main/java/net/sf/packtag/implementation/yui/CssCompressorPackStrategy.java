/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.implementation.yui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;

import net.sf.packtag.strategy.CssRewritePackStrategy;
import net.sf.packtag.strategy.PackException;

import com.yahoo.platform.yui.compressor.CssCompressor;



/**
 * CSS Compressor by Isaac Schlueter (http://foohack.com).
 * The Compressor comes with the YuiCompressor.
 *
 * @author Daniel Galán y Martins
 */
public class CssCompressorPackStrategy extends CssRewritePackStrategy {

	private static final int LINEBREAK_AFTER_CHARACTERS = 8000;


	/**
	 * Packs a CSS resource while replacing every occurring background image
	 * path to an absolute one (unless it isn't already an absolute one). This
	 * ensures working images when re-mapping the base-path to a different root
	 * under the web application.
	 */
	public String pack(final InputStream resourceAsStream, final Charset charset, final String path) throws PackException {
		try {
			InputStreamReader isr = new InputStreamReader(resourceAsStream, charset);
			CssCompressor cssc = new CssCompressor(isr);
			StringWriter sw = new StringWriter();
			cssc.compress(sw, LINEBREAK_AFTER_CHARACTERS);
			sw.flush();
			String result = rewritePath(sw.toString(), path);
			return result;
		}
		catch (IOException ex) {
			throw new PackException(ex);
		}
	}

}
