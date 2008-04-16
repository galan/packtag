/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 15.03.2008 - 19:21:44
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 21:39:53 $
 * Revision:      $Revision: 1.1 $
 * 
 * $Log: FileFetcher.java,v $
 * Revision 1.1  2008/03/15 21:39:53  danielgalan
 * Collects all files by extension.
 *
 */
package net.sf.packtag.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;



/**
 * Collects all files by extension.
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.1 $
 */
public class FileFetcher {

	private final boolean includeSubdirectories;
	private final String resourceExtension;
	private final String contextPath;


	public FileFetcher(final boolean includeSubdirectories, final String resourceExtension, final String contextPath) {
		this.includeSubdirectories = includeSubdirectories;
		this.resourceExtension = resourceExtension;
		this.contextPath = contextPath;
	}


	/**
	 * Collects all files of the resources recursivly.
	 * This could also be accomplished with return value, but would be more inefficent.
	 * 
	 * @param contextRootDirectory Root directory of the contextPth
	 * @param directory Current directory
	 * @param resources List where found resources will be added to
	 */
	private void determineFiles(final File contextRootDirectory, final File directory, final List resources) {
		File[] files = directory.listFiles(new FileFilter() {

			public boolean accept(final File pathname) {
				return (pathname.isFile() && pathname.getName().endsWith("." + getResourceExtension())) || pathname.isDirectory();
			}

		});
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory() && isIncludeSubdirectories()) {
					determineFiles(contextRootDirectory, files[i], resources);
				}
				else if (files[i].isFile()) {
					String fileAbsolute = files[i].getAbsolutePath();
					String fileContextless = fileAbsolute.substring(contextRootDirectory.getAbsolutePath().length(), fileAbsolute.length());
					fileContextless = fileContextless.replaceAll("\\\\", "/");

					resources.add(getContextPath() + fileContextless);
				}
			}
		}
	}


	public List fetchFiles(final File contextRootDirectory, final File startDirectory) {
		List files = new ArrayList();
		determineFiles(contextRootDirectory, startDirectory, files);
		return files;
	}


	public boolean isIncludeSubdirectories() {
		return includeSubdirectories;
	}


	public String getResourceExtension() {
		return resourceExtension;
	}


	public String getContextPath() {
		return contextPath;
	}

}
