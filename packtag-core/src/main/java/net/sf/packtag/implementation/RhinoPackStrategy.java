/**
 * Project pack:tag >> http://packtag.sf.net
 * 
 * This software is published under the terms of the LGPL License version 2.1, a
 * copy of which has been included with this distribution in the 'lgpl.txt'
 * file.
 * 
 * Creation date: 14.08.2007 - 23:25:29 Last author: $Author: danielgalan $ Last
 * modified: $Date: 2007/09/23 14:37:31 $ Revision: $Revision: 1.2 $
 * 
 * $Log: RhinoPackStrategy.java,v $ Revision 1.2 2007/09/23 14:37:31 danielgalan
 * Added charset support
 * 
 * Revision 1.1 2007/08/27 22:36:08 danielgalan work in progress
 * 
 */
package net.sf.packtag.implementation;

import java.io.InputStream;
import java.nio.charset.Charset;

import net.sf.packtag.strategy.AbstractPackStrategy;
import net.sf.packtag.strategy.PackException;



/**
 * work in progress
 * 
 * @author danielgalan
 * @version $Revision: 1.2 $
 */
public class RhinoPackStrategy extends AbstractPackStrategy {

	public String pack(final InputStream resourceAsStream, final Charset charset, final String path) throws PackException {
		return null;
	}

}
