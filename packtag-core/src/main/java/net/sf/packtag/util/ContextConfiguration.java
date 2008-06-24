/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 *
 * Creation date: 10.07.2007 - 23:52:52
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 16:30:45 $
 * Revision:      $Revision: 1.11 $
 *
 * $Log: ContextConfiguration.java,v $
 * Revision 1.11  2008/03/15 16:30:45  danielgalan
 * external resource packaging can be configurated
 *
 * Revision 1.10  2008/02/10 20:59:49  danielgalan
 * SafeLogger
 *
 * Revision 1.9  2008/01/26 12:53:35  danielgalan
 * virtual path for combined resources (per resourcetype)
 *
 * Revision 1.8  2008/01/22 22:17:44  danielgalan
 * new setting for the virtual servlet path for combined resources
 *
 * Revision 1.7  2007/11/06 23:12:40  danielgalan
 * CacheServletPath is obsolete
 *
 * Revision 1.6  2007/10/31 09:24:19  danielgalan
 * configuration for isTrackingResources
 *
 * Revision 1.5  2007/10/12 21:50:40  danielgalan
 * default Charset solution
 *
 * Revision 1.4  2007/09/23 14:37:31  danielgalan
 * Added charset support
 *
 * Revision 1.3  2007/09/23 13:54:24  danielgalan
 * Applied backward compatibility patch from qxo@users.sourceforge.net
 *
 * Revision 1.2  2007/08/27 22:50:21  danielgalan
 * settings into property files instead of web.xml
 *
 * Revision 1.1  2007/07/11 23:01:31  danielgalan
 * - 2.2
 *   - enhancement: caching header improved (servlet)
 *   - servlet url-mapping can be changed now (you have to set "packtag.cache.servlet.path" to the same value)
 *   - enhancement: polished the reference at http://www.galan.de/projects/packtag
 *
 */
package net.sf.packtag.util;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.servlet.ServletContext;



/**
 * Keeps track of the configuration settings in the /WEB-INF/packtag.properties and /WEB-INF/packtag.user.properties file.
 * Note: The configuration is not longet stored in the web.xml.
 *
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.11 $
 */
public class ContextConfiguration {

	private static final String SLASH = "/";

	public final static String CACHE_TYPE_FILE = "file";
	public final static String CACHE_TYPE_SERVLET = "servlet";
	public final static String CACHE_TYPE_DISABLED = "disabled";

	private final static String TRUE = Boolean.TRUE.toString();
	private final static String FALSE = Boolean.FALSE.toString();

	private static Properties properties;

	/** Singleton for the cache file path */
	private static String cacheFilePath;

	/** Singleton for the Charset */
	private static Charset charset;


	protected static Properties loadPropertyFile(final ServletContext context) {
		Properties result = new Properties();
		try {
			InputStream is = context.getResourceAsStream("/WEB-INF/packtag.properties");
			if (is != null) {
				result.load(is);
			}
		}
		catch (Exception ex) {
			SafeLogger.error(ContextConfiguration.class, "Could not load file: /WEB-INF/packtag.properties", ex);
		}
		try {
			InputStream is = context.getResourceAsStream("/WEB-INF/packtag.user.properties");
			if (is != null) {
				result.load(is);
			}
		}
		catch (Exception ex) {
			SafeLogger.error(ContextConfiguration.class, "Could not load file: /WEB-INF/packtag.user.properties", ex);
		}
		return result;
	}


	protected static Properties getProperties(final ServletContext context) {
		if (properties == null) {
			synchronized (ContextConfiguration.class) {
				if (properties == null) {
					properties = loadPropertyFile(context);
				}
			}
		}
		return properties;
	}


	protected static String getCacheType(final ServletContext context) {
		return getProperty(context, "cache.type", CACHE_TYPE_SERVLET);
	}


	public static boolean isCachetypeDisabled(final ServletContext context) {
		return CACHE_TYPE_DISABLED.equalsIgnoreCase(getCacheType(context));
	}


	public static boolean isCachetypeServlet(final ServletContext context) {
		return CACHE_TYPE_SERVLET.equalsIgnoreCase(getCacheType(context));
	}


	public static boolean isCachetypeFile(final ServletContext context) {
		return CACHE_TYPE_FILE.equalsIgnoreCase(getCacheType(context));
	}


	/** Should the files timestamp be checked, so the resource is renewed on change? */
	public static boolean isFileCheckTimestamps(final ServletContext context) {
		return getProperty(context, "resources.checktimestamps", TRUE).equalsIgnoreCase(TRUE);
	}


	/** Should errors be shown, or hidden in the dark, resulting in unexpected behaviour/layout? (Not recommended) */
	public static boolean isHideErros(final ServletContext context) {
		return getProperty(context, "hide.errors", FALSE).equalsIgnoreCase(TRUE);
	}


	public static boolean isTrackingResources(final ServletContext context) {
		return getProperty(context, "resources.tracking", TRUE).equalsIgnoreCase(TRUE);
	}


	public static boolean isExternalResourcesEnabled(final ServletContext context) {
		return getProperty(context, "resources.external", TRUE).equalsIgnoreCase(TRUE);
	}


	public static String getPackStrategyClassName(final ServletContext context, final String resourceType) {
		return getProperty(context, resourceType + ".strategy");
	}


	protected static String getProperty(final ServletContext context, final String key) {
		String property = getProperties(context).getProperty(key);
		if (null == property) {
			property = context.getInitParameter("packtag." + key);
		}
		return property;
	}


	protected static String getProperty(final ServletContext context, final String key, final String defaultValue) {
		final String property = getProperty(context, key);
		return property != null ? property : defaultValue;
	}


	public static String getCacheFilePath(final ServletContext context) {
		if (cacheFilePath == null) {
			synchronized (ContextConfiguration.class) {
				if (cacheFilePath == null) {
					String path = getProperty(context, "cache.file.path", "pack");
					if (path.startsWith(SLASH)) {
						path = path.substring(1, path.length());
					}
					if (path.endsWith(SLASH)) {
						path = path.substring(0, path.length() - 1);
					}
					cacheFilePath = path;
				}
			}
		}
		return cacheFilePath;
	}


	public static String getCacheServletCombinedPath(final ServletContext context, final String strategyName) {
		String path = getProperty(context, "cache.servlet.combined." + strategyName + ".path", "");
		if (path.startsWith(SLASH)) {
			path = path.substring(1, path.length());
		}
		if (!path.equals("") && !path.endsWith(SLASH)) {
			path = path + SLASH;
		}
		return path;
	}


	public static Charset getCharset(final ServletContext context) {
		if (charset == null) {
			synchronized (ContextConfiguration.class) {
				if (charset == null) {
					String resourcesCharsetName = getProperty(context, "resources.charset");
					String charsetName = getProperty(context, "charset"); // for backward compatibility
					if (resourcesCharsetName != null) {
						charset = Charset.forName(resourcesCharsetName);
					}
					else if (charsetName != null) {
						charset = Charset.forName(charsetName);
					}
					else {
						charset = new CharsetUtil().getDefaultCharset();
					}
				}
			}
		}
		return charset;
	}

}
