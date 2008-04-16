/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 04.11.2007 - 19:45:56
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2007/11/06 23:11:55 $
 * Revision:      $Revision: 1.1 $
 * 
 * $Log: URIUtils.java,v $
 * Revision 1.1  2007/11/06 23:11:55  danielgalan
 * Helperclass for rewriting the URIs
 *
 */
package net.sf.packtag.util;

/**
 * Helps finding the absolute location of resources
 * 
 * @author  danielgalan
 * @version $Revision: 1.1 $
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
		String result = path.replaceAll("/./", "/");
		if (result.indexOf("/./") != -1) {
			result = cleanCurrentdirs(result);
		}
		return result;
	}

}
