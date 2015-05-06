/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.implementation.yui;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;

import net.sf.packtag.strategy.AbstractPackStrategy;
import net.sf.packtag.strategy.PackException;

import org.mozilla.javascript.ErrorReporter;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;



/**
 * YuiCompressor from Julien Lecomte (http://www.julienlecomte.net)
 *
 * @author Daniel Galán y Martins
 */
public class YuiCompressorPackStrategy extends AbstractPackStrategy {

	private static final int LINEBREAK_AFTER_CHARACTERS = 8000;


	public String pack(final InputStream resourceAsStream, final Charset charset, final String path) throws PackException {
		try {
			InputStreamReader isr = new InputStreamReader(resourceAsStream, charset);
			ErrorReporter er = new JavaScriptErrorReporter();
			JavaScriptCompressor jsc = new JavaScriptCompressor(isr, er);
			StringWriter sw = new StringWriter();
			jsc.compress(sw, LINEBREAK_AFTER_CHARACTERS, true, true, true, true);
			sw.flush();
			return sw.toString();
		}
		catch (Exception ex) {
			throw new PackException(ex);
		}
	}

}
