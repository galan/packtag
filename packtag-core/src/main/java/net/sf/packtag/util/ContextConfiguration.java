/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.util;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import jakarta.servlet.ServletContext;

import net.sf.packtag.cache.provider.DefaultCacheProvider;



/**
 * Keeps track of the configuration settings in the /WEB-INF/packtag.properties and /WEB-INF/packtag.user.properties file.
 * Note: The configuration is not longet stored in the web.xml.
 *
 * @author  Daniel Galán y Martins
 */
public class ContextConfiguration {

	private static final String SLASH = "/";

	public final static String CACHE_TYPE_FILE = "file";
	public final static String CACHE_TYPE_SERVLET = "servlet";
	public final static String CACHE_TYPE_DISABLED = "disabled";

	public final static String SCRIPT_ASYNCDEFER_XHTML = "XHTML";
	public final static String SCRIPT_ASYNCDEFER_HTML5 = "HTML5";

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


	protected static String getScriptAsyncDefer(final ServletContext context) {
		return getProperty(context, "script.asyncdefer", SCRIPT_ASYNCDEFER_XHTML);
	}


	public static boolean isScriptAsyncDeferXhtml(final ServletContext context) {
		return SCRIPT_ASYNCDEFER_XHTML.equalsIgnoreCase(getScriptAsyncDefer(context));
	}


	public static boolean isScriptAsyncDeferHtml5(final ServletContext context) {
		return SCRIPT_ASYNCDEFER_HTML5.equalsIgnoreCase(getScriptAsyncDefer(context));
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


	public static boolean isUrlRewriteEnabled(final ServletContext context) {
		return getProperty(context, "resources.rewrite", FALSE).equalsIgnoreCase(TRUE);
	}


	public static Class getCacheProviderClass(final ServletContext context) {
		Class result = null;
		String cacheProvider = getProperty(context, "cache.provider", DefaultCacheProvider.class.getName());
		try {
			result = Class.forName(cacheProvider);
		}
		catch (ClassNotFoundException ex) {
			SafeLogger.error(ContextConfiguration.class, "Could not instantiate CacheProvider", ex);
		}
		return result;
	}


	public static String getCacheProviderPath(final ServletContext context) {
		return getProperty(context, "cache.provider.path");
	}

}
