/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 06.09.2009 - 18:51:56
 * Last author:   $Author: danielgalan $
 * Last modified: $Date:$
 * Revision:      $Revision:$
 * 
 * $Log:$
 */
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
 * @author  tentacle
 * @version $Revision:$
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
