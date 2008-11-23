/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 13.11.2008 - 23:52:36
 * Last author:   $Author: danielgalan $
 * Last modified: $Date:$
 * Revision:      $Revision:$
 * 
 * $Log:$
 */
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
 * @version $Revision:$
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
