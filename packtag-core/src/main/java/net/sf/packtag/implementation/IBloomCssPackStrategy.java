/**
 * Project pack:tag >> http://packtag.sf.net
 * 
 * This software is published under the terms of the LGPL License version 2.1, a
 * copy of which has been included with this distribution in the 'lgpl.txt'
 * file.
 * 
 * Last author: $Author: danielgalan $ Last modified: $Date: 2007/11/12 22:52:19 $
 * Revision: $Revision: 1.3 $
 * 
 * $Log: IBloomCssPackStrategy.java,v $ Revision 1.3 2007/11/12 22:52:19
 * danielgalan origin website to comment added
 * 
 * Revision 1.2 2007/09/23 14:37:31 danielgalan Added charset support
 * 
 * Revision 1.1 2007/04/22 19:04:25 danielgalan pack.tag moved from subversion
 * to good old CVS
 * 
 */
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
 * @version $Revision: 1.3 $
 */
public class IBloomCssPackStrategy extends CssRewritePackStrategy {

	private static final String IBLOOM_REGEX_COMMENTS = "/\\*[^*]*\\*+([^/][^*]*\\*+)*/";
	private static final String IBLOOM_REGEX_SPACES = "(\r\n)|(\r)|(\n)|(\t)|(  +)";
	private static final String EMPTY_STRING = "";


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
		result = result.replaceAll(IBLOOM_REGEX_SPACES, EMPTY_STRING);
		result = rewritePath(result, path);
		return result.trim();
	}

}
