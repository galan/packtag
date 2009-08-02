/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2007/09/23 14:37:31 $
 * Revision:      $Revision: 1.2 $
 * 
 * $Log: AbstractPackStrategy.java,v $
 * Revision 1.2  2007/09/23 14:37:31  danielgalan
 * Added charset support
 *
 * Revision 1.1  2007/04/22 19:04:23  danielgalan
 * pack.tag moved from subversion to good old CVS
 *
 */
package net.sf.packtag.strategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.packtag.util.URIUtils;



/**
 * A super Strategy with supporting methods.
 * 
 * @author Daniel Galán y Martins
 * @version $Revision: 1.2 $
 */
public abstract class CssRewritePackStrategy extends AbstractPackStrategy {

	private static final String REGEX_URLS = "((href|src)\\s*=|@import|url)\\s*(\\(\\s*\"|\\(\\s*'|\\(|'|\")+([\\w#/_%\\.&\\-\\?\\+]+)('\\s*\\)|\"\\s*\\)|\"|\\))+";
	private static final Pattern urlRegexPattern = Pattern.compile(REGEX_URLS);


	protected String rewritePath(final String resource, final String path) {
		String result = resource;
		if (path != null && path.length() > 0) {
			StringBuffer sb = new StringBuffer();
			/*
			 * If a path is provided we assume that we have to rewrite the
			 * background image urls in the css file to absolute urls to work
			 * even when re-mapped to a virtual path after compressing and
			 * provided via the pack servlet.
			 */
			String cssBasePath = URIUtils.cleanRelativePath(path);
			int idxRoot = cssBasePath.lastIndexOf("/");
			if (idxRoot != -1) {
				cssBasePath = cssBasePath.substring(0, idxRoot + 1);
			}
			idxRoot = cssBasePath.indexOf("/", 0);
			if (idxRoot > 0) {
				cssBasePath = cssBasePath.substring(idxRoot);
			}

			Matcher m = urlRegexPattern.matcher(result);
			while(m.find()) {
				/*
				 * int gc = m.groupCount(); for (int i = 0; i <= gc; i++) {
				 * System.out.println("group #" + i + " = " + m.group(i)); }
				 * System.out.println(path + m.group(4));
				 */
				String rewrittenUrl = URIUtils.cleanRelativePath(cssBasePath + m.group(4));
				m.appendReplacement(sb, "$1$2$3" + rewrittenUrl + "$5");

			}
			m.appendTail(sb);
			result = sb.toString();
		}
		return result;
	}

}
