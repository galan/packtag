/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 06.09.2009 - 17:46:23
 * Last author:   $Author: danielgalan $
 * Last modified: $Date:$
 * Revision:      $Revision:$
 * 
 * $Log:$
 */
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
 * @author  tentacle
 * @version $Revision:$
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
