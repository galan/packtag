/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2007/09/23 14:37:31 $
 * Revision:      $Revision: 1.2 $
 * 
 * $Log: PackStrategy.java,v $
 * Revision 1.2  2007/09/23 14:37:31  danielgalan
 * Added charset support
 *
 * Revision 1.1  2007/04/22 19:04:23  danielgalan
 * pack.tag moved from subversion to good old CVS
 *
 */
package net.sf.packtag.strategy;

import java.io.InputStream;
import java.nio.charset.Charset;



/**
 * A Strategy has to implement this Interface, the algorithm needs to return the packed
 * resource. 
 * 
 * @author Daniel Galán y Martins
 * @version $Revision: 1.2 $
 */
public interface PackStrategy {

	/**
	 * A algorithm returns a compressed resource from the Stream.  
	 * Called once, when the resource is requested the first time.
	 *
	 * @param resourceAsStream The requested resource which should be compressed
	 * @return The compressed resource
	 */
	public String pack(InputStream resourceAsStream, Charset charset) throws PackException;

}
