/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 12.03.2008 - 22:44:51
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 16:37:42 $
 * Revision:      $Revision: 1.1 $
 * 
 * $Log: RequestUtil.java,v $
 * Revision 1.1  2008/03/15 16:37:42  danielgalan
 * Generic Helper for request specific informations
 *
 */
package net.sf.packtag.util;

import javax.servlet.http.HttpServletRequest;



/**
 * Provides some Helper Methodes regarding to the sended request. 
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.1 $
 */
public class RequestUtil {

	/**
	* Tests gzip capabilities of the users browser.
	* 
	* @param request The current request
	* @return Indicating GZIP support
	*/
	public static boolean isGZipSupported(final HttpServletRequest request) {
		String acceptEncoding = request.getHeader(HttpHeader.ACCEPTED_ENCODING);
		return (acceptEncoding != null) && (acceptEncoding.indexOf(HttpHeader.GZIP) != -1);
	}

}
