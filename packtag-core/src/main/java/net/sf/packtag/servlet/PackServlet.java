/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 17:21:11 $
 * Revision:      $Revision: 1.10 $
 * 
 * $Log: PackServlet.java,v $
 * Revision 1.10  2008/03/15 17:21:11  danielgalan
 * more Header externalized
 *
 * Revision 1.9  2008/03/15 16:38:31  danielgalan
 * Header / request cleaning
 *
 * Revision 1.8  2007/11/12 22:57:24  danielgalan
 * renamed sqp to absolutePath and fqp to mappedPath
 *
 * Revision 1.7  2007/11/06 23:10:25  danielgalan
 * Switch to new URI-semantic
 *
 * Revision 1.6  2007/10/13 20:20:22  danielgalan
 * added "Content-Type" header for MIME-Type and charset (UTF-8)
 *
 * Revision 1.5  2007/09/29 21:22:27  danielgalan
 * evaluate "If-None-Match" header
 *
 * Revision 1.4  2007/08/27 22:37:29  danielgalan
 * expire date now ten years in the future (that should be enough ;))
 *
 * Revision 1.3  2007/07/12 22:10:40  danielgalan
 * removed system.out
 *
 * Revision 1.2  2007/07/11 23:01:30  danielgalan
 * - 2.2
 *   - enhancement: caching header improved (servlet)
 *   - servlet url-mapping can be changed now (you have to set "packtag.cache.servlet.path" to the same value)
 *   - enhancement: polished the reference at http://www.galan.de/projects/packtag
 *
 * Revision 1.1  2007/04/22 19:04:25  danielgalan
 * pack.tag moved from subversion to good old CVS
 *
 */
package net.sf.packtag.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.packtag.cache.PackCache;
import net.sf.packtag.cache.Resource;
import net.sf.packtag.util.HttpHeader;
import net.sf.packtag.util.RequestUtil;



/**
 * Servlet implementation class for Servlet: PackServlet
 * This Servlet return the packed resources, identified thru the mapped path (and hashcode).
 * Only needed when Servlet is choosen as cachetype (default).
 * The output is gzip-compressed.
 * 
 * @author Daniel Galán y Martins
 * @version $Revision: 1.10 $
 */
public class PackServlet extends HttpServlet {

	private static final long serialVersionUID = 6588877416667767264L;

	private static final long ONE_YEAR = 31536000000L;
	private static final long TEN_YEARS = ONE_YEAR * 10L;
	private static final String ETAG_PREFIX = "pack";

	private static final String CONTENT_TYPE = "Content-Type";
	private static final String CHARSET_UTF8 = ";charset=utf-8";


	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		Resource resource = PackCache.getResourceByMappedPath(getServletContext(), request.getRequestURI());

		if (resource == null) {
			response.sendError(404, "The requested packed resource was not found.");
		}
		else {
			setHeaders(response, resource);

			if (isNotModifed(request, resource)) {
				response.setStatus(304);
			}
			else {
				writeResource(request, response, resource);
			}
		}
	}


	private void writeResource(final HttpServletRequest request, final HttpServletResponse response, final Resource resource) throws IOException {
		if (RequestUtil.isGZipSupported(request)) {
			response.setHeader(HttpHeader.CONTENT_ENCODING, HttpHeader.GZIP);
			response.getOutputStream().write(resource.getGzippedResource());
		}
		else {
			response.getWriter().write(resource.getMinifedResource());
		}
	}


	/**
	 * Check if the "If_None-Match" Header is send. When this equals to the resource, it is not
	 * modified, and hasn't to be delivered again (Status code 304: Not modifed).
	 * This happens when someone e.g. has the resource allready loaded, and presses the reload-button.
	 * (Doesn't seems to work on IE6, like always)
	 * 
	 * @return true if the user already has the resource
	 */
	private boolean isNotModifed(final HttpServletRequest request, final Resource resource) {
		StringBuffer selfMatch = new StringBuffer();
		selfMatch.append(ETAG_PREFIX);
		selfMatch.append(resource.getMinifiedHashcode());
		String self = selfMatch.toString();
		return self.equals(request.getHeader(HttpHeader.IF_NONE_MATCH));
	}


	private void setHeaders(final HttpServletResponse response, final Resource resource) {
		// It won't be cached by proxies, because of the Cache-Control: private header. Let the Browser cache the resource
		response.setHeader(HttpHeader.CACHE_CONTROL, HttpHeader.CACHE_CONTROL_PRIVATE);

		// Don't let the resource expire
		response.setDateHeader(HttpHeader.EXPIRES, new Date().getTime() + TEN_YEARS); // Expires after ten year

		// Funny header, see here: http://en.wikipedia.org/wiki/HTTP_ETag
		response.setHeader(HttpHeader.ETAG, ETAG_PREFIX + Integer.toString(resource.getMinifiedHashcode()));

		// See http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html - Section 14.44 "Vary"
		// This needs to be set to work right with proxies (and apache)
		//response.setHeader("Vary", "Accept-Encoding");
		// Thats not true, the hashcodes identify a resource unique, make it possible to the proxy to cache it
		// Force caching, experimental, therefor disabled
		//response.setHeader("Vary", "Accept-Encoding"); // this needs to be set to work right with proxies (and apache)

		//response.setHeader("Pragma", "cache");  // HTTP 1.0
		//response.setHeader("Cache-Control", "public");  // HTTP 1.1

		response.setHeader(CONTENT_TYPE, resource.getMimeType() + CHARSET_UTF8);
		response.setHeader(HttpHeader.BRANDING_HEADER, HttpHeader.BRANDING_VALUE);
	}

}
