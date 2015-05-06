/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * Collects all files by extension.
 *
 * @author  Daniel Galán y Martins
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
		// Because listFiles does not return filenames in a deterministic order,
		// make sure that we sort the files so that the hashed filename is
		// consistent. Otherwise file globbing could be dangerous when serving
		// content from multiple different machines.
		Collections.sort(files);
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
