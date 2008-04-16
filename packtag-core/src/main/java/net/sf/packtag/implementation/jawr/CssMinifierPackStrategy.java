/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 10.02.2008 - 11:52:19
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 21:34:08 $
 * Revision:      $Revision: 1.2 $
 * 
 * $Log: CssMinifierPackStrategy.java,v $
 * Revision 1.2  2008/03/15 21:34:08  danielgalan
 * Name correction
 *
 * Revision 1.1  2008/03/15 16:34:19  danielgalan
 * CSS Minifier by the jawr project
 *
 */
package net.sf.packtag.implementation.jawr;

import java.io.InputStream;
import java.nio.charset.Charset;

import net.jawr.web.minification.CSSMinifier;
import net.sf.packtag.strategy.AbstractPackStrategy;
import net.sf.packtag.strategy.PackException;



/**
 * Wraps the CSSMinifier by Jordi Hernández Sellés, published under the Apache Licence 2.0.
 * See the licence.txt for more information about the Apache Licence, or go to his website:
 * https://jawr.dev.java.net/
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.2 $
 */
public class CssMinifierPackStrategy extends AbstractPackStrategy {

	public String pack(final InputStream resourceAsStream, final Charset charset) throws PackException {
		CSSMinifier minifier = new CSSMinifier();
		StringBuffer resource = new StringBuffer(resourceToString(resourceAsStream, charset));
		StringBuffer result = minifier.minifyCSS(resource);
		return result.toString();
	}

}
