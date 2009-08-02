/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 16:35:54 $
 * Revision:      $Revision: 1.17 $
 * 
 * $Log: BaseTag.java,v $
 * Revision 1.17  2008/03/15 16:35:54  danielgalan
 * external resource packaging can be configurated
 *
 * Revision 1.16  2008/01/26 12:53:28  danielgalan
 * virtual path for combined resources (per resourcetype)
 *
 * Revision 1.15  2008/01/22 22:17:44  danielgalan
 * new setting for the virtual servlet path for combined resources
 *
 * Revision 1.14  2007/11/19 19:52:29  danielgalan
 * fix for external resources
 *
 * Revision 1.13  2007/11/12 22:57:24  danielgalan
 * renamed sqp to absolutePath and fqp to mappedPath
 *
 * Revision 1.12  2007/11/06 23:10:44  danielgalan
 * Switch to new URI-semantic
 *
 * Revision 1.11  2007/10/31 09:21:30  danielgalan
 * extracted helper methods from logic
 *
 * Revision 1.10  2007/10/13 20:22:42  danielgalan
 * Mimetype abstraction and UTF-8 target encoding
 *
 * Revision 1.9  2007/10/12 20:57:02  danielgalan
 * Files before branch
 *
 * Revision 1.8  2007/09/25 22:12:36  danielgalan
 * Charset (Java 1.6 Method used): See here
 * http://sourceforge.net/forum/forum.php?thread_id=1831376&forum_id=665160
 *
 * Revision 1.7  2007/09/23 18:48:37  danielgalan
 * Characterset
 *
 * Revision 1.6  2007/09/23 14:37:31  danielgalan
 * Added charset support
 *
 * Revision 1.5  2007/08/27 22:51:11  danielgalan
 * settings into property files instead of web.xml
 *
 * Revision 1.4  2007/07/11 23:01:31  danielgalan
 * - 2.2
 *   - enhancement: caching header improved (servlet)
 *   - servlet url-mapping can be changed now (you have to set "packtag.cache.servlet.path" to the same value)
 *   - enhancement: polished the reference at http://www.galan.de/projects/packtag
 *
 * Revision 1.3  2007/05/17 11:55:44  danielgalan
 * fix: no default cachetype was set *do'h*
 *
 * Revision 1.2  2007/05/02 21:29:19  danielgalan
 * last fixes for 2.0, attribute media
 *
 * Revision 1.1  2007/04/22 19:04:24  danielgalan
 * pack.tag moved from subversion to good old CVS
 *
 */
package net.sf.packtag.tag;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.packtag.util.ContextConfiguration;



/**
 * Tag Base class for common Operations
 * 
 * @author Daniel Gal�n y Martins
 * @version $Revision: 1.17 $
 */
public abstract class BaseTag extends BodyTagSupport {

	private static final long serialVersionUID = -6185105963103670264L;

	/** Well, an empty String  */
	protected final static String EMPTY_STRING = "";
	/** The slash Character */
	protected final static String SLASH = "/";

	/** Encoding String for 8-Bit Unicode, which is the standard output encoding */
	protected final static String UTF_8 = "UTF-8";

	/** Start tag name for a combined resource */
	private final static String SRC_TAG_START = "<src>";
	/** End tag name for a combined resource */
	private final static String SRC_TAG_END = "</src>";

	protected final static String PROTOCOLL_HTTP = "http://";
	protected final static String PROTOCOLL_HTTPS = "https://";

	/** The name of the request attribute, that keeps track of the delivered resources */
	private static final String REQUEST_RESOURCES = "packtagRequestResources";

	/** Name of the Source attribute */
	private final static String ATTRIBUTE_SRC = "src";
	/** Name of the compress attribute */
	private final static String ATTRIBUTE_MINIFY = "minify";

	/** Monoton for the Apache Jakarta Taglib validation */
	private static Boolean standardTaglibAvailable = null;

	/** The entered path of the source */
	private String src;
	/** If compress is set to false by the user, no minification is done */
	private Boolean minify = Boolean.TRUE;
	/** I enabled is set to false, the original resource will be referenced */
	private boolean enabled = true;


	protected String determineAbsolutePath(final String source) {
		String result = source;
		if (result != null) {
			result = result.trim();
			if (result.startsWith(SLASH)) {
				result = getContextPath() + result;
			}
			else {
				if (!isExternalResource(result)) {
					HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
					String uri = request.getRequestURI();
					// cut a possible file
					String partPath = uri.substring(0, uri.lastIndexOf(SLASH) + 1);

					// we are not on the basepath
					//if (!partPath.equals(SLASH)) {
					result = partPath + result;
					//}
				}
			}
		}
		return result;
	}


	/** Returns a List with the delivered resources, in this request  */
	protected List getRequestResources() {
		List requestResources = (List)pageContext.getRequest().getAttribute(REQUEST_RESOURCES);
		if (requestResources == null) {
			requestResources = new ArrayList();
			pageContext.getRequest().setAttribute(REQUEST_RESOURCES, requestResources);
		}
		return requestResources;
	}


	/** Return true, if the resource is already delivered in this request */
	protected boolean isResourceDelivered(final String absolutePath) {
		return getRequestResources().contains(absolutePath);
	}


	/** Returns true, if all the resources are already delivered in this request */
	protected boolean areResourcesDelivered(final List absolutePathes) {
		return getRequestResources().containsAll(absolutePathes);
	}


	/** Adds the resource to the request-List */
	protected void addDeliveredResource(final String absolutePath) {
		getRequestResources().add(absolutePath);
	}


	/** Adds the resources to the request-List */
	protected void addDeliveredResources(final List absolutePathes) {
		getRequestResources().addAll(absolutePathes);
	}


	/** Returns the source of the resource */
	public String getSrc() {
		return src;
	}


	/** Sets the resource in the tag */
	public void setSrc(final String src) {
		if (isStandardTaglibAvailable()) {
			this.src = (String)evaluate(ATTRIBUTE_SRC, src, String.class);
		}
		else {
			this.src = src;
		}
	}


	/** Should the resource be minifed? */
	public Boolean isMinify() {
		return minify;
	}


	/** Sets if the resource should be minified */
	public void setMinify(final String minify) {
		if (isStandardTaglibAvailable()) {
			this.minify = new Boolean((String)evaluate(ATTRIBUTE_MINIFY, minify, String.class));
		}
		else {
			this.minify = new Boolean(minify);
		}
	}


	/** Should the resource be handled by pack:tag or just written out? */
	public boolean isEnabled() {
		return enabled && !isCachetypeDisabled();
	}


	/** Sets if the resource should be handled or just written out */
	public void setEnabled(final Boolean enabled) {
		if (enabled != null) {
			this.enabled = enabled.booleanValue();
		}
	}


	/** Sets if the resource should be handled or just written out */
	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}


	/** Configuration; returns the path, where the minifed resources will be put in (when cache type is file) */
	protected String getCacheFilePath() {
		return ContextConfiguration.getCacheFilePath(pageContext.getServletContext());
	}


	/** Configuration; returns the (virtual) path, where the combined resources will be fetched from (when cache type is servlet) */
	protected String getCacheServletCombinedPath(final String strategyName) {
		return ContextConfiguration.getCacheServletCombinedPath(pageContext.getServletContext(), strategyName);
	}


	/** Configuration; returns true, when the resources just should be written out */
	protected boolean isCachetypeDisabled() {
		return ContextConfiguration.isCachetypeDisabled(pageContext.getServletContext());
	}


	/** Configuration; return true, if the cachetype is set to servlet */
	protected boolean isCachetypeServlet() {
		return ContextConfiguration.isCachetypeServlet(pageContext.getServletContext());
	}


	/** Configuration; return true, if the cachetype is set to file */
	protected boolean isCachetypeFile() {
		return ContextConfiguration.isCachetypeFile(pageContext.getServletContext());
	}


	/** Configuration; returns true, if the timestamps of the files should be checked each time, they are requested */
	protected boolean isFileCheckTimestamps() {
		return ContextConfiguration.isFileCheckTimestamps(pageContext.getServletContext());
	}


	/** Configuration; returns true, if the errors should not result in a HTTP 500 */
	protected boolean isHideErros() {
		return ContextConfiguration.isHideErros(pageContext.getServletContext());
	}


	/** Configuration; returns true, if the resources should be keept in the request, in order to not write them out twice */
	protected boolean isTrackingResources() {
		return ContextConfiguration.isTrackingResources(pageContext.getServletContext());
	}


	/** Configuration; returns true, if external resources should be downloaded, minifed and compressed. */
	protected boolean isExternalResourcesEnabled() {
		return ContextConfiguration.isExternalResourcesEnabled(pageContext.getServletContext());
	}


	/** Configuration; returns the name of the minification strategy */
	protected String getPackStrategyClassName(final String resourceType) {
		return ContextConfiguration.getPackStrategyClassName(pageContext.getServletContext(), resourceType);
	}


	/** Configuration; returns the Characterset, the files are read with */
	protected Charset getCharset() {
		return ContextConfiguration.getCharset(pageContext.getServletContext());
	}


	/** Configuration; returns if rewrite pathes is enabled */
	protected boolean isUrlRewriteEnabled() {
		return ContextConfiguration.isUrlRewriteEnabled(pageContext.getServletContext());
	}


	/** Throws an error directly (500), or hides it away (the exception will be printed as stacktrace). */
	protected void promoteError(final String message, final Exception ex) throws JspTagException {
		if (isHideErros()) {
			// just write comment
			try {
				pageContext.getOut().write("<!-- ");
				pageContext.getOut().write(message);
				pageContext.getOut().write(" -->");
			}
			catch (IOException ioex) {
				// nada
			}
		}
		else {
			throw new JspTagException(message); // , ex);
		}
	}


	/** Compresses a String to a gzipped bytearray. */
	protected byte[] gzipString(final String text, final String charsetName) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream zos = new GZIPOutputStream(baos);
		byte[] bytes = text.getBytes(charsetName);
		//byte[] bytes = new String(text.getBytes(getCharset().name()), getCharset().name()).getBytes();
		//byte[] bytes = getCharset().encode(text).array(); // Doesn't works well: https://sourceforge.net/tracker/?func=detail&atid=928887&aid=1795663&group_id=189328
		//byte[] bytes = text.getBytes(getCharset()); // Java 1.6
		zos.write(bytes, 0, bytes.length);
		zos.finish();
		return baos.toByteArray();
	}


	/** Retrieves the contextpath */
	protected String getContextPath() {
		String contextPath = null;
		if (HttpServletRequest.class.isAssignableFrom(pageContext.getRequest().getClass())) {
			contextPath = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
		}
		else {
			contextPath = getContextPath(pageContext.getServletContext());
		}
		return contextPath;
	}


	/**
	 * Retrieves the context path of the web application from the servlet context (altenative approach).
	 *
	 * @param context the current servlet context
	 * @return the derived context path, guaranteed non-null
	 */
	private static String getContextPath(final ServletContext context) {
		// old way to determine context path:
		//  String tempdir = EMPTY_STRING + context.getAttribute("javax.servlet.context.tempdir");
		//  int lastSlash = tempdir.lastIndexOf(File.separator);
		//  if ((tempdir.length() - 1) > lastSlash) {
		//    logHomePropName = tempdir.substring(lastSlash + 1) + ".log.home";
		//  }
		String contextPath = EMPTY_STRING;

		try {
			//use a more standard way to obtain the context path name
			//which should work across all servers. The tmpdir technique
			//(above) depends upon the naming scheme that Tomcat uses.
			String path = context.getResource(SLASH).getPath();

			//first remove trailing slash, then take what's left over
			//which should be the context path less the preceeding
			//slash such as "MyContext"
			contextPath = path.substring(0, path.lastIndexOf(SLASH));
			contextPath = contextPath.substring(contextPath.lastIndexOf(SLASH) + 1);
		}
		catch (Exception e) {
		}

		return SLASH + contextPath;
	}


	/** Parses the requests with the Apache evaluation standard library (if availiable) */
	protected Object evaluate(final String attributeName, final String expression, final Class expectedType) {
		Object result = null;
		try {
			//Class cls = Class.forName("org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager");
			//Method m = cls.getMethod("evaluate", new Class[] {String.class, String.class, Class.class, Tag.class, PageContext.class});
			//m.invoke(null, new Object[] {attributeName, expression, expectedType, this, pageContext});
			result = org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager.evaluate(attributeName, expression, expectedType, this, pageContext);
		}
		catch (JspException je) {
			try {
				pageContext.getOut().write("<!-- ");
				je.printStackTrace(new PrintWriter(pageContext.getOut()));
				pageContext.getOut().write(" -->");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	/**
	 * Parses the body of the Tag, this approach doesn't need any XML Parser and is therefore probably faster
	 * (and better it doesn't has dependencies to those).
	 * BUT it is therefore fragile (No comments (if they are in the src-tag) nor whitespaces are allowed in the
	 * tags (e.g. &lt;src &gt; wouldn't work).
	 * 
	 * @return A List with shortQualifiedPaths
	 */
	protected List parseBody(final String bodyString) {
		int bodyIndex = 0;
		List resources = new ArrayList();
		while(bodyIndex < (bodyString.length() - 1)) {
			int indexSrcStart = bodyString.indexOf(SRC_TAG_START, bodyIndex);
			if (indexSrcStart == -1) {
				return resources;
			}
			int indexSrcEnd = bodyString.indexOf(SRC_TAG_END, bodyIndex);
			if (indexSrcEnd == -1) {
				return resources;
			}
			String source = bodyString.substring(indexSrcStart + 5, indexSrcEnd).trim();
			resources.add(determineAbsolutePath(source));
			bodyIndex = indexSrcEnd + 6;
		}
		return resources;
	}


	/** Returns true if the classes from the Apache Standard Taglibrary are available */
	protected boolean isStandardTaglibAvailable() {
		if (standardTaglibAvailable == null) {
			synchronized (BaseTag.class) {
				if (standardTaglibAvailable == null) {
					try {
						standardTaglibAvailable = new Boolean(Class.forName("org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager") != null);
					}
					catch (ClassNotFoundException e) {
						standardTaglibAvailable = Boolean.FALSE;
					}
				}
			}
		}
		return standardTaglibAvailable.booleanValue();
	}


	protected boolean isExternalResource(final String source) {
		if (source == null) {
			return false;
		}
		return source.startsWith(PROTOCOLL_HTTP) || source.startsWith(PROTOCOLL_HTTPS);
	}


	protected ServletContext getServletContext() {
		return pageContext.getServletContext();
	}

}
