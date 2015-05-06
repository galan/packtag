/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.util;

import javax.servlet.http.HttpServletRequest;



/**
 * Provides some Helper Methodes regarding to the sended request.
 *
 * @author  Daniel Galán y Martins
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
