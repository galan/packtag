/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.cache.provider;

import java.util.Hashtable;
import java.util.Set;

import net.sf.packtag.cache.Resource;



/**
 * The default CacheProvider, which is derived from the
 * static cache in version 3.4.
 *
 * @author  Daniel Galán y Martins
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
