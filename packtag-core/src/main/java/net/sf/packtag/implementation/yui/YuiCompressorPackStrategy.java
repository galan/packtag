/**
 * Project pack:tag >> http://packtag.sf.net
 * 
 * This software is published under the terms of the LGPL License version 2.1, a
 * copy of which has been included with this distribution in the 'lgpl.txt'
 * file.
 * 
 * Creation date: 14.08.2007 - 23:48:35 Last author: $Author: danielgalan $ Last
 * modified: $Date: 2008/02/10 20:38:33 $ Revision: $Revision: 1.6 $
 * 
 * $Log: YuiCompressorPackStrategy.java,v $ Revision 1.6 2008/02/10 20:38:33
 * danielgalan SafeLogger
 * 
 * Revision 1.5 2007/10/30 19:50:25 danielgalan yui 2.2.5 support
 * 
 * Revision 1.4 2007/09/23 14:56:25 danielgalan YuiCompressor 2.1.2 support
 * 
 * Revision 1.3 2007/09/23 14:37:31 danielgalan Added charset support
 * 
 * Revision 1.2 2007/08/31 20:07:42 danielgalan Updated YuiCompressor from a
 * modified 1.0 to standard 2.1
 * 
 * Revision 1.1 2007/08/27 22:36:23 danielgalan YuiCompressor
 * 
 */
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
 * @version $Revision: 1.6 $
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
