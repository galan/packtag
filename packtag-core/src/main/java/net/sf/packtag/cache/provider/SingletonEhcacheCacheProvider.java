/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.cache.provider;

import net.sf.ehcache.CacheManager;



/**
 * An ehcache CacheProvider, which will use the singleton ehcache.
 * You will need two caches:
 * "packtagResourcesAbsolutePath" and "packtagResourcesMappedPath"
 *
 * @author  Daniel Galán y Martins
 */
public class SingletonEhcacheCacheProvider extends AbstractEhcacheProvider {

	protected CacheManager getCacheManager() {
		return CacheManager.getInstance();
	}

}
