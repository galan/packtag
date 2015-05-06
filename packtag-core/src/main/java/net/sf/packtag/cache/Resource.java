/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.cache;

import java.io.Serializable;



/**
 * Represents a Cached Resource
 *
 * @author  Daniel Galán y Martins
 */
public class Resource implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	private String absolutePath;
	private String mappedPath;
	private String minifedResource;
	private byte[] gzippedResource;
	private final int minifiedHashcode;
	private long fileTimestamp;
	private boolean combined;
	private String mimeType;
	private String wildcardAbsolutePath;


	public Resource(final boolean combined, final int minifiedHashcode) {
		setCombined(combined);
		this.minifiedHashcode = minifiedHashcode;
	}


	protected void setCombined(final boolean combined) {
		this.combined = combined;
	}


	public boolean isCombined() {
		return combined;
	}


	public long getFileTimestamp() {
		if (isCombined()) {
			return -1;
		}
		return fileTimestamp;
	}


	public void setFileTimestamp(final long fileTimestamp) {
		this.fileTimestamp = fileTimestamp;
	}


	public String getMappedPath() {
		return mappedPath;
	}


	public void setMappedPath(final String mappedPath) {
		this.mappedPath = mappedPath;
	}


	public byte[] getGzippedResource() {
		return gzippedResource;
	}


	public void setGzippedResource(final byte[] gzippedResource) {
		this.gzippedResource = gzippedResource;
	}


	public String getMinifedResource() {
		return minifedResource;
	}


	public void setMinifedResource(final String minifedResource) {
		this.minifedResource = minifedResource;
		// I don't set the minified hashcode here, because
		// cachetype "file" doesn't need to pollute the memory
		// therefore I set the hashcode in the constructor.
		// And recalculating the hashcode here isn't usefull.
	}


	public int getMinifiedHashcode() {
		return minifiedHashcode;
	}


	public String getAbsolutePath() {
		return absolutePath;
	}


	public void setAbsolutePath(final String absolutePath) {
		this.absolutePath = absolutePath;
	}


	public String getMimeType() {
		return mimeType;
	}


	public void setMimeType(final String mimeType) {
		this.mimeType = mimeType;
	}


	public String toString() {
		return getAbsolutePath() + "@" + getMinifiedHashcode();
	}


	public void setWildcardAbsolutePaths(final String wildcardAbsolutePath) {
		this.wildcardAbsolutePath = wildcardAbsolutePath;
	}


	public String getWildcardAbsolutePaths() {
		return wildcardAbsolutePath;
	}


	public String[] getWildcardAbsolutePathsSplitted() {
		if (wildcardAbsolutePath != null) {
			return wildcardAbsolutePath.substring(1, wildcardAbsolutePath.length() - 1).split(", ");
		}
		return new String[] {};
	}


	public Resource cloneObject() {
		try {
			return (Resource)clone();
		}
		catch (CloneNotSupportedException ex) {
		}
		return null;
	}


	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}


	public boolean isWildcard() {
		return getWildcardAbsolutePaths() != null;
	}

}
