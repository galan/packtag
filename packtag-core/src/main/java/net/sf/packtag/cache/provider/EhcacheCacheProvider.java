/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.cache.provider;

import javax.servlet.ServletContext;

import net.sf.ehcache.CacheManager;
import net.sf.packtag.util.ContextConfiguration;



/**
 * An ehcache CacheProvider, which will create a new ehcache from
 * the cache.provider.path setting.
 * You will need two caches:
 * "packtagResourcesAbsolutePath" and "packtagResourcesMappedPath"
 *
 * @author  Daniel Galán y Martins
 */
public class EhcacheCacheProvider extends AbstractEhcacheProvider {

	private CacheManager manager;
	private String path;


	public void init(final ServletContext context) {
		super.init(context);
		path = ContextConfiguration.getCacheProviderPath(context);
	}


	protected CacheManager getCacheManager() {
		if (manager == null) {
			manager = CacheManager.create(path);
		}
		return manager;
	}

}
