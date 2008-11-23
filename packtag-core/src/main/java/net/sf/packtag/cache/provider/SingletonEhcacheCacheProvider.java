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

import net.sf.ehcache.CacheManager;



/**
 * An ehcache CacheProvider, which will use the singleton ehcache.
 * You will need two caches:
 * "packtagResourcesAbsolutePath" and "packtagResourcesMappedPath"
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision:$
 */
public class SingletonEhcacheCacheProvider extends AbstractEhcacheProvider {

	protected CacheManager getCacheManager() {
		return CacheManager.getInstance();
	}

}
