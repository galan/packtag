/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.strategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.packtag.util.URIUtils;



/**
 * A super Strategy with supporting methods.
 *
 * @author Daniel Galán y Martins
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
