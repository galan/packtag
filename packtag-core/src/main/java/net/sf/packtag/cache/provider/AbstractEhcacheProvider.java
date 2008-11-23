/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 15.11.2008 - 09:55:05
 * Last author:   $Author: danielgalan $
 * Last modified: $Date:$
 * Revision:      $Revision:$
 * 
 * $Log:$
 */
package net.sf.packtag.cache.provider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.packtag.cache.Resource;



/**
 * Basic ehcache specific cache provider implementation
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision:$
 */
public abstract class AbstractEhcacheProvider extends AbstractCacheProvider {

	private final Cache resourcesAbsolutePath;
	private final Cache resourcesMappedPath;


	public AbstractEhcacheProvider() {
		resourcesAbsolutePath = getCacheManager().getCache("packtagResourcesAbsolutePath");
		resourcesMappedPath = getCacheManager().getCache("packtagResourcesMappedPath");
	}


	protected abstract CacheManager getCacheManager();


	public void clearCache() {
		resourcesAbsolutePath.removeAll();
		resourcesMappedPath.removeAll();
	}


	public boolean existResource(final String absolutePath) {
		return resourcesAbsolutePath.isKeyInCache(absolutePath);
	}


	public Resource getResourceByAbsolutePath(final String absolutePath) {
		Resource result = null;
		Element elem = resourcesAbsolutePath.get(absolutePath);
		if (elem != null) {
			result = (Resource)elem.getValue();
		}
		return result;
	}


	public Resource getResourceByMappedPath(final String mappedPath) {
		Resource result = null;
		Element elem = resourcesMappedPath.get(mappedPath);
		if (elem != null) {
			result = (Resource)elem.getValue();
		}
		return result;
	}


	public void store(final Resource resource, final boolean clearDependingCombinedResources) {
		resourcesAbsolutePath.put(new Element(resource.getAbsolutePath(), resource));
		resourcesMappedPath.put(new Element(resource.getMappedPath(), resource));

		if (clearDependingCombinedResources) {
			clearDependingCombinedResources(resource);
		}
	}


	public Set getAbsolutePathKeys() {
		return convertList(resourcesAbsolutePath.getKeysNoDuplicateCheck());
	}


	public Set getMappedPathKeys() {
		return convertList(resourcesMappedPath.getKeysNoDuplicateCheck());
	}


	private Set convertList(final List list) {
		Set result = new HashSet();
		for (int i = 0; i < list.size(); i++) {
			result.add(list.get(i));
		}
		return result;
	}


	public void removeAbsolutePath(final String absolutePath) {
		resourcesAbsolutePath.remove(absolutePath);
	}


	public void removeMappedPath(final String mappedPath) {
		resourcesMappedPath.remove(mappedPath);
	}

}
