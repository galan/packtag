/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 12.03.2008 - 22:33:52
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 17:21:11 $
 * Revision:      $Revision: 1.2 $
 * 
 * $Log: HttpHeader.java,v $
 * Revision 1.2  2008/03/15 17:21:11  danielgalan
 * more Header externalized
 *
 * Revision 1.1  2008/03/15 16:37:19  danielgalan
 * headers
 *
 */
package net.sf.packtag.util;

/**
 * List of HTTP header names
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.2 $
 */
public class HttpHeader {

	public static final String ACCEPTED_ENCODING = "Accept-Encoding";
	public static final String CONTENT_ENCODING = "Content-Encoding";
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String IF_NONE_MATCH = "If-None-Match";
	public static final String CACHE_CONTROL = "Cache-Control";
	public static final String EXPIRES = "Expires";
	public static final String ETAG = "ETag";

	public static final String CACHE_CONTROL_PRIVATE = "private";
	public static final String GZIP = "gzip";

	public static final String BRANDING_HEADER = "X-Powered-By";
	public static final String BRANDING_VALUE = "pack:tag";

}
