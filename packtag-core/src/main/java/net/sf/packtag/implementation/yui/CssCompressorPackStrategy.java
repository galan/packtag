/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL License version 2.1, a
 * copy of which has been included with this distribution in the 'lgpl.txt'
 * file.
 *
 * Creation date: 31.08.2007 - 21:14:54 Last author: $Author: danielgalan $ Last
 * modified: $Date: 2007/11/06 23:03:45 $ Revision: $Revision: 1.4 $
 *
 * $Log: CssCompressorPackStrategy.java,v $ Revision 1.4 2007/11/06 23:03:45
 * danielgalan Update Author
 *
 * Revision 1.3 2007/09/23 14:56:25 danielgalan YuiCompressor 2.1.2 support
 *
 * Revision 1.2 2007/09/23 14:37:31 danielgalan Added charset support
 *
 * Revision 1.1 2007/08/31 20:02:39 danielgalan CssCompressor from Isaac
 * Schlueter
 *
 */
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
 * @version $Revision: 1.4 $
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
