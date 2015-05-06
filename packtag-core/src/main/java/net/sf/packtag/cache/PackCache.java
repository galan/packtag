/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.cache;

import javax.servlet.ServletContext;

import net.sf.packtag.util.ContextConfiguration;



/**
 * Singleton to the Cache
 *
 * @author  Daniel Galán y Martins
 */
public class PackCache {

	private static PackCache instance;
	private CacheProvider provider;


	//private final ResourceCache resourceCache = new ResourceCache();

	private static PackCache getInstance() {
		if (instance == null) {
			synchronized (PackCache.class) {
				if (instance == null) {
					instance = new PackCache();
				}
			}
		}
		return instance;
	}


	private CacheProvider getResourceCache(final ServletContext context) {
		if (provider == null) {
			Class providerClass = ContextConfiguration.getCacheProviderClass(context);
			provider = new CacheProviderFactory().build(providerClass);
			provider.init(context);
		}
		return provider;
	}


	public static Resource getResourceByAbsolutePath(final ServletContext context, final String absolutePath) {
		return getInstance().getResourceCache(context).getResourceByAbsolutePath(absolutePath);
	}


	public static Resource getResourceByMappedPath(final ServletContext context, final String mappedPath) {
		return getInstance().getResourceCache(context).getResourceByMappedPath(mappedPath);
	}


	public static boolean existResource(final ServletContext context, final String absolutePath) {
		return getInstance().getResourceCache(context).existResource(absolutePath);
	}


	public static void store(final ServletContext context, final Resource resource, final boolean clearDependingCombinedResources) {
		getInstance().getResourceCache(context).store(resource, clearDependingCombinedResources);
	}


	public static void clearCache(final ServletContext context) {
		getInstance().getResourceCache(context).clearCache();
	}

}
