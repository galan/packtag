/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 13.11.2008 - 23:32:33
 * Last author:   $Author: danielgalan $
 * Last modified: $Date:$
 * Revision:      $Revision:$
 * 
 * $Log:$
 */
package net.sf.packtag.cache.provider;

import java.util.Hashtable;
import java.util.Set;

import net.sf.packtag.cache.Resource;



/**
 * The default CacheProvider, which is derived from the
 * static cache in version 3.4.
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision:$
 */
public class DefaultCacheProvider extends AbstractCacheProvider {

	private final Hashtable resourcesAbsolutePath = new Hashtable();
	private final Hashtable resourcesMappedPath = new Hashtable();


	public Resource getResourceByAbsolutePath(final String absolutePath) {
		return (Resource)resourcesAbsolutePath.get(absolutePath);
	}


	public Resource getResourceByMappedPath(final String mappedPath) {
		return (Resource)resourcesMappedPath.get(mappedPath);
	}


	public boolean existResource(final String absolutePath) {
		return resourcesAbsolutePath.containsKey(absolutePath);
	}


	public void store(final Resource resource, final boolean clearDependingCombinedResources) {
		resourcesAbsolutePath.put(resource.getAbsolutePath(), resource);
		resourcesMappedPath.put(resource.getMappedPath(), resource);

		if (clearDependingCombinedResources) {
			clearDependingCombinedResources(resource);
		}
	}


	public void clearCache() {
		resourcesAbsolutePath.clear();
		resourcesMappedPath.clear();
	}


	public Set getAbsolutePathKeys() {
		return resourcesAbsolutePath.keySet();
	}


	public Set getMappedPathKeys() {
		return resourcesMappedPath.keySet();
	}


	public void removeAbsolutePath(final String absolutePath) {
		resourcesAbsolutePath.remove(absolutePath);
	}


	public void removeMappedPath(final String mappedPath) {
		resourcesMappedPath.remove(mappedPath);
	}

}
