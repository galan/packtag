/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.util;

/**
 * Helps finding the absolute location of resources
 *
 * @author  Daniel Galán y Martins
 */
public class URIUtils {

	public static String cleanRelativePath(final String path) {
		String result = cleanSubdirs(path);
		result = cleanCurrentdirs(result);
		return result;
	}


	private static String cleanSubdirs(final String path) {
		String result = path;
		//int indexFirstSubdir = result.indexOf("../");
		int indexSubdir = result.indexOf("../");
		while(indexSubdir != -1) {
			String firstPart = result.substring(0, indexSubdir - 1);
			int lastSlash = firstPart.lastIndexOf("/");
			if (lastSlash != -1) {
				firstPart = firstPart.substring(0, lastSlash);
			}
			String lastPart = result.substring(indexSubdir + 2, result.length());
			//result = result.indexOf("");
			result = firstPart + lastPart;
			indexSubdir = result.indexOf("../");
		}
		return result;
	}


	private static String cleanCurrentdirs(final String path) {
		String result = path.replaceAll("/\\./", "/");
		if (result.indexOf("/./") != -1) {
			result = cleanCurrentdirs(result);
		}
		return result;
	}

}
