/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 *
 * Creation date: 30.10.2007 - 09:53:32
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 21:35:49 $
 * Revision:      $Revision: 1.9 $
 *
 * $Log: PackTag.java,v $
 * Revision 1.9  2008/03/15 21:35:49  danielgalan
 * Only subdirectory, or recursivly
 *
 * Revision 1.8  2008/03/15 16:36:44  danielgalan
 * all the new features for 3.3
 *
 * Revision 1.7  2008/02/10 21:00:15  danielgalan
 * Default is now servlet
 *
 * Revision 1.6  2008/01/26 12:53:54  danielgalan
 * virtual path for combined resources (per resourcetype), resourcereload bug fixed
 *
 * Revision 1.5  2008/01/22 22:18:08  danielgalan
 * append the virtual path for combined resources on cache type servlet
 *
 * Revision 1.4  2007/11/19 19:52:44  danielgalan
 * testing for enhancing the external resources
 *
 * Revision 1.3  2007/11/12 22:57:24  danielgalan
 * renamed sqp to absolutePath and fqp to mappedPath
 *
 * Revision 1.2  2007/11/06 23:10:51  danielgalan
 * Switch to new URI-semantic
 *
 * Revision 1.1  2007/10/31 09:22:27  danielgalan
 * compress renamed to minify, tracking of resource to identify double resources
 *
 */
package net.sf.packtag.tag;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

import net.sf.packtag.cache.PackCache;
import net.sf.packtag.cache.Resource;
import net.sf.packtag.implementation.DisabledPackStrategy;
import net.sf.packtag.strategy.PackException;
import net.sf.packtag.strategy.PackStrategy;
import net.sf.packtag.util.FileFetcher;
import net.sf.packtag.util.HttpHeader;
import net.sf.packtag.util.URIUtils;



/**
 * Tag base class for resource-specific JspTags.
 *
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.9 $
 */
public abstract class PackTag extends BaseTag {

	private static final long serialVersionUID = -3714373962322644845L;


	/** Called method, when the tag start */
	public int doStartTag() throws JspTagException {
		return EVAL_BODY_BUFFERED;
	}


	/** Called method, when the tag ends */
	public int doEndTag() throws JspException {
		String absolutePath = determineAbsolutePath(getSrc());
		boolean apDefined = (absolutePath != null) && !absolutePath.equals(EMPTY_STRING);
		try {
			// Attribute src is available, use this
			if (apDefined) {
				handleSingleResource(absolutePath, true);
			}
			// A body with multiple resources is defined
			else {
				handleCombinedResource();
			}
		}
		catch (PackException pex) {
			promoteError(pex.getMessage(), pex);
		}
		catch (Exception ex) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("Pack did not perfom successfull on ");
			buffer.append(apDefined ? "'" + absolutePath + "': " : "combined Resource: ");
			buffer.append(ex.getMessage());
			promoteError(buffer.toString(), ex);
		}
		return EVAL_PAGE;
	}


	/**
	 * Packs a single Resource, and writes the output.
	 *
	 * @return true if resources have loaded or reloaded
	 */
	private boolean handleSingleResource(final String absolutePath, final boolean writePackedResource) throws Exception {
		boolean reloaded = false;
		if (isAbsolutePathWildcard(absolutePath)) {
			reloaded = handleWildcardResource(absolutePath, writePackedResource);
		}
		else if (!isExternalResourcesEnabled() && isExternalResource(absolutePath)) {
			writeResouce(pageContext.getOut(), absolutePath);
		}
		else {
			if (isEnabled()) {
				reloaded = handleSingleResourceDelegate(absolutePath);
				Resource resource = PackCache.getResourceByAbsolutePath(getServletContext(), absolutePath);

				if (writePackedResource && (!isTrackingResources() || !isResourceDelivered(absolutePath))) {
					writeResouce(pageContext.getOut(), resource.getMappedPath());
					addDeliveredResource(resource.getAbsolutePath());
				}
			}
			else {
				// if packing is not enabled, we just write out the resources
				writeResouce(pageContext.getOut(), absolutePath);
			}
		}
		return reloaded;
	}


	private boolean handleSingleResourceDelegate(final String absolutePath) throws Exception {
		boolean reloaded = false;
		Resource resource = null;
		if (PackCache.existResource(getServletContext(), absolutePath)) {
			resource = PackCache.getResourceByAbsolutePath(getServletContext(), absolutePath);
			if (hasResourceChanged(resource)) {
				resource = reloadSingleResource(absolutePath);
				reloaded = true;
			}
		}
		else {
			resource = reloadSingleResource(absolutePath);
			reloaded = true;
		}
		return reloaded;
	}


	private boolean handleWildcardResource(final String absolutePath, final boolean writePackedResource) throws Exception {
		boolean reloaded = false;
		// Reloading is outside the enabled-block on wildcard paths, because elsewise no resource can be written.
		Resource resource = null;
		if (PackCache.existResource(getServletContext(), absolutePath)) {
			resource = PackCache.getResourceByAbsolutePath(getServletContext(), absolutePath);
			if (hasResourceChanged(resource)) {
				resource = reloadWildcardResource(absolutePath);
				reloaded = true;
			}
		}
		else {
			resource = reloadWildcardResource(absolutePath);
			reloaded = true;
		}

		if (isEnabled()) {
			if (writePackedResource && (!isTrackingResources() || !isResourceDelivered(absolutePath))) {
				writeResouce(pageContext.getOut(), resource.getMappedPath());
				addDeliveredResource(resource.getAbsolutePath());
			}
		}
		else {
			// if packing is not enabled, we just write out the resources
			String[] aps = resource.getWildcardAbsolutePathsSplitted();
			for (int i = 0; i < aps.length; i++) {
				writeResouce(pageContext.getOut(), aps[i]);
			}
		}
		return reloaded;
	}


	protected boolean isAbsolutePathWildcard(final String absolutePath) {
		return absolutePath.endsWith("/**") || absolutePath.endsWith("/*");
		//return absolutePath.matches("^.*/\\*?\\*$");
	}


	/**
	 * Packs multiple Resources and write them as one Resource to the output.
	 * If just one File has changed, the whole combined Resource will be reloaded.
	 */
	private void handleCombinedResource() throws Exception {
		BodyContent bc = getBodyContent();
		String bodyString = bc.getString().trim();
		if ((bodyString != null) && !bodyString.equals(EMPTY_STRING)) {
			// reloaded becomes true if one resource has been reloaded/modified
			boolean reloaded = false;
			List absolutePaths = parseBody(bodyString);
			if (absolutePaths.size() == 0) {
				throw new PackException("No resources were specified.");
			}

			Iterator iterAbsolutePaths = absolutePaths.iterator();
			while(iterAbsolutePaths.hasNext()) {
				String currentAbsolutePath = (String)iterAbsolutePaths.next();
				reloaded |= handleSingleResource(currentAbsolutePath, false);
				if (!isExternalResourcesEnabled() && isExternalResource(currentAbsolutePath)) {
					iterAbsolutePaths.remove();
				}
			}

			// just write the combined resource if enabled, otherwise they
			// are already written to the output by handleSingleResource(..)
			if (isEnabled()) {
				Resource resource = handleMultipleAbsolutePaths(reloaded, absolutePaths);
				if (!isTrackingResources() || !areResourcesDelivered(absolutePaths)) {
					writeResouce(pageContext.getOut(), resource.getMappedPath());
					addDeliveredResources(absolutePaths);
				}
			}
		}
		// either a src nor a bodycontent has been defined
		else {
			throw new PackException("No resources were specified");
		}
	}


	private Resource handleMultipleAbsolutePaths(final boolean reloaded, final List absolutePaths) throws Exception {
		Resource resource = null;
		String combinedAbsolutePaths = absolutePaths.toString();
		if (PackCache.existResource(getServletContext(), combinedAbsolutePaths) && !reloaded) {
			resource = PackCache.getResourceByAbsolutePath(getServletContext(), combinedAbsolutePaths);
		}
		else {
			resource = reloadCombinedResource(absolutePaths);
		}
		return resource;
	}


	private List gatherWildcardAbsolutePaths(final String absolutePathWithWildcard) {
		String wildcardLessPath = absolutePathWithWildcard.substring(0, absolutePathWithWildcard.lastIndexOf(SLASH));
		String startRealPath = pageContext.getServletContext().getRealPath(getContextlessPath(wildcardLessPath));

		File startDirectory = new File(startRealPath);
		File contextRoot = new File(pageContext.getServletContext().getRealPath(SLASH));

		boolean includeSubdirectories = absolutePathWithWildcard.endsWith("/**");
		FileFetcher ff = new FileFetcher(includeSubdirectories, getResourceExtension(), getContextPath());
		return ff.fetchFiles(contextRoot, startDirectory);
	}


	/* Here, a combined InputStream will be constructed from the given resources. */
	/*
	private InputStream getCombinedResourceStream(final List absolutePaths) throws PackException {
	    InputStream[] streams = new InputStream[absolutePaths.size()];
	    for (int i = 0; i < absolutePaths.size(); i++) {
	        String absolutePath = (String)absolutePaths.get(i);
	        InputStream resourceAsStream = getResourceStream(absolutePath);
	        streams[i] = resourceAsStream;
	    }
	    return new CombinedInputStream(streams);
	}
	*/

	private InputStream getResourceStream(final String absolutePath) throws PackException {
		InputStream result = null;
		if (isExternalResource(absolutePath)) {
			InputStream urlInputStream = null;
			try {
				URL url = new URL(absolutePath);
				URLConnection conn = url.openConnection();
				conn.setRequestProperty(HttpHeader.CONTENT_TYPE, getMimeType());
				urlInputStream = conn.getInputStream();
			}
			catch (IOException ioe) {
				throw new PackException("Could not load external resource: " + absolutePath, ioe);
			}
			result = urlInputStream;
		}
		else {
			String contextlessPath = getContextlessPath(absolutePath);
			if (isFileCheckTimestamps()) {
				String realPath = pageContext.getServletContext().getRealPath(contextlessPath);
				File file = new File(realPath);
				try {
					result = new BufferedInputStream(new FileInputStream(file));
				}
				catch (FileNotFoundException fnfe) {
					throw new PackException("Resource not found (" + absolutePath + ")", fnfe);
				}
			}
			else {
				//TODO Aaargh... this just works every second time
				// But WHY .. if you have a solution, please! give me a hand. Thanks
				// (Because of this situation, this method was born (with the realpath madness above))
				result = pageContext.getServletContext().getResourceAsStream(contextlessPath);
			}
		}
		return result;
	}


	/** Compresses the resource and stores it in the cache and as file (if cachetype is file) */
	private Resource reloadSingleResource(final String absolutePath) throws Exception {
		InputStream stream = getResourceStream(absolutePath);

		String rewriteAbsolutePath = isUrlRewriteEnabled() ? absolutePath : null;
		String packedResource = getPackStrategyDelegate().pack(stream, getCharset(), rewriteAbsolutePath);

		stream.close();
		Resource resource = new Resource(false, packedResource.hashCode());
		// Needs to hold in memory for every cachetype, because of several usage in combined and wildcard resources
		resource.setMinifedResource(packedResource);
		if (isCachetypeServlet()) {
			resource.setGzippedResource(gzipString(packedResource, UTF_8));
			resource.setMimeType(getMimeType());
		}
		else if (isCachetypeFile()) {
			storeFile(resource, packedResource);
		}
		if (isFileCheckTimestamps()) {
			resource.setFileTimestamp(getFileLastModifiedTimeStamp(getContextlessPath(absolutePath)));
		}
		resource.setAbsolutePath(absolutePath);
		resource.setMappedPath(determineMappedPath(resource));

		PackCache.store(getServletContext(), resource, true);
		return resource;
	}


	/** Compresses resources found, depending on the pattern */
	private Resource reloadWildcardResource(final String absolutePath) throws Exception {
		Resource result = null;
		boolean reloaded = false;
		List files = gatherWildcardAbsolutePaths(absolutePath);
		Iterator iterFiles = files.iterator();
		while(iterFiles.hasNext()) {
			String file = (String)iterFiles.next();
			reloaded |= handleSingleResourceDelegate(file);
		}
		result = handleMultipleAbsolutePaths(reloaded, files);
		result = result.cloneObject();
		result.setWildcardAbsolutePaths(result.getAbsolutePath());
		result.setAbsolutePath(absolutePath);
		PackCache.store(getServletContext(), result, true);

		//resource = handleWildcardResource(files);
		return result;
	}


	/** Compresses multiple resources and stores them in the cache and as file (if cachetype is file) */
	private Resource reloadCombinedResource(final List absolutePaths) throws Exception {
		// Assumption: All resources allready have been loaded/reloaded by handleSingleResource

		StringBuffer minifedBuffer = new StringBuffer();
		Iterator iterAps = absolutePaths.iterator();
		while(iterAps.hasNext()) {
			String currentAbsolutePath = (String)iterAps.next();
			Resource resource = PackCache.getResourceByAbsolutePath(getServletContext(), currentAbsolutePath);
			minifedBuffer.append(resource.getMinifedResource());
			minifedBuffer.append("\n");
		}

		ByteArrayInputStream stream = new ByteArrayInputStream(minifedBuffer.toString().getBytes(UTF_8));

		//InputStream stream = getCombinedResourceStream(absolutePaths);
		String packedResource = getPackStrategyDelegate().pack(stream, getCharset(), null);

		Resource resource = new Resource(true, packedResource.hashCode());
		// Needs to hold in memory for every cachetype, because of several usage in combined and wildcard resources
		resource.setMinifedResource(packedResource);
		if (isCachetypeServlet()) {
			resource.setGzippedResource(gzipString(packedResource, UTF_8));
			resource.setMimeType(getMimeType());
		}
		else if (isCachetypeFile()) {
			storeFile(resource, packedResource);
		}
		resource.setAbsolutePath(absolutePaths.toString());
		resource.setMappedPath(determineMappedPath(resource));

		PackCache.store(getServletContext(), resource, false);
		return resource;
	}


	/** Saves the minified resource to disk */
	private void storeFile(final Resource resource, final String packedResource) throws IOException {
		String cacheFilePath = getCacheFilePath() + SLASH;
		String realPath = pageContext.getServletContext().getRealPath(SLASH) + cacheFilePath;
		File fileRealPath = new File(realPath);
		if (!fileRealPath.exists()) {
			fileRealPath.mkdirs();
		}
		File fileAbsolutePath = new File(realPath + determineFileName(resource));
		if (fileAbsolutePath.exists()) {
			fileAbsolutePath.delete();
		}
		fileAbsolutePath.createNewFile();
		Writer writer = new OutputStreamWriter(new FileOutputStream(fileAbsolutePath), UTF_8);
		writer.write(packedResource);
		writer.flush();
		writer.close();
	}


	public String determineMappedPath(final Resource resource) throws Exception {
		StringBuffer buffer = new StringBuffer();
		if (isCachetypeDisabled()) {
			buffer.append(resource.getAbsolutePath());
		}
		else if (isCachetypeServlet()) {
			if (resource.isCombined() || isExternalResource(resource.getAbsolutePath())) {
				buffer.append(getContextPath());
				buffer.append(SLASH);
				buffer.append(getCacheServletCombinedPath(getStrategyName()));
				if (isExternalResource(resource.getAbsolutePath())) {
					buffer.append("external.");
				}
				else {
					buffer.append("combined.");
				}
				buffer.append(getResourceExtension());
			}
			else {
				String cleanPath = URIUtils.cleanRelativePath(resource.getAbsolutePath());
				int indexWebInf = cleanPath.indexOf("/WEB-INF/");
				if (indexWebInf >= 0) {
					buffer.append(cleanPath.substring(0, indexWebInf));
					buffer.append(cleanPath.substring(indexWebInf + 8, cleanPath.length()));
				}
				else {
					buffer.append(cleanPath);
				}
			}
			buffer.append(".h");
			buffer.append(resource.getMinifiedHashcode());
			buffer.append(".pack");
		}
		else if (isCachetypeFile()) {
			buffer.append(getContextPath());
			buffer.append(SLASH);
			buffer.append(getCacheFilePath());
			buffer.append(SLASH);
			buffer.append(determineFileName(resource));
		}
		return buffer.toString();
	}


	private String determineFileName(final Resource resource) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("h");
		buffer.append(resource.getMinifiedHashcode());
		buffer.append(".");
		buffer.append(getResourceExtension());
		return buffer.toString();
	}


	private boolean hasResourceChanged(final Resource resource) {
		boolean result = false;
		if (isFileCheckTimestamps() && !isExternalResource(resource.getAbsolutePath())) {
			if (resource.isWildcard()) {
				//Resource wildcardResource = PackCache.getResourceByAbsolutePath(resource.getWildcardAbsolutePaths());
				String[] wildcardAp = resource.getWildcardAbsolutePathsSplitted();
				for (int i = 0; i < wildcardAp.length; i++) {
					Resource r = PackCache.getResourceByAbsolutePath(getServletContext(), wildcardAp[i]);
					result |= hasResourceChanged(r);
				}
				//result = hasResourceChanged(wildcardResource);
			}
			else {
				long lastModified = getFileLastModifiedTimeStamp(getContextlessPath(resource.getAbsolutePath()));
				if (lastModified != resource.getFileTimestamp()) {
					result = true;
				}
			}
		}
		return result;
	}


	private long getFileLastModifiedTimeStamp(final String path) {
		String realPath = pageContext.getServletContext().getRealPath(path);
		File file = new File(realPath);
		return file.lastModified();
	}


	protected PackStrategy getPackStrategyDelegate() throws Exception {
		return Boolean.TRUE.equals(isMinify()) ? getPackStrategy() : new DisabledPackStrategy();
	}


	/** Writes the minified resource to the generated html-page */
	protected abstract void writeResouce(JspWriter writer, String path) throws Exception;


	/** Returns the PackStrategy, depending on the resourcetype */
	protected PackStrategy getPackStrategy() throws Exception {
		String className = getPackStrategyClassName(getStrategyName());
		if ((className == null) || className.equals(EMPTY_STRING)) {
			return getResourceDefaultStrategy();
		}
		return (PackStrategy)Class.forName(className).newInstance();
	}


	protected abstract String getStrategyName();


	protected abstract PackStrategy getResourceDefaultStrategy();


	/** Returns the typical file-extension of the concrete resource */
	protected abstract String getResourceExtension();


	/** Returns the MIME-Type of the resource */
	protected abstract String getMimeType();


	private String getContextlessPath(final String absolutePath) {
		return absolutePath.substring(getContextPath().length(), absolutePath.length());
	}

}
