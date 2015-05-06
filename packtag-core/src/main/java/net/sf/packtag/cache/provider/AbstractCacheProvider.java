/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.cache.provider;

import javax.servlet.ServletContext;

import net.sf.packtag.cache.CacheProvider;
import net.sf.packtag.cache.Resource;



/**
 * Basic CacheProvider functions
 *
 * @author  Daniel Galán y Martins
 */
public abstract class AbstractCacheProvider implements CacheProvider {

	public void init(final ServletContext context) {
	}


	protected synchronized void clearDependingCombinedResources(final Resource resource) {
		String absolutePath = resource.getAbsolutePath();
		Object[] keys = getAbsolutePathKeys().toArray();
		for (int i = 0; i < keys.length; i++) {
			Resource currentResource = getResourceByAbsolutePath((String)keys[i]);
			// Difference between combined and wildcard resources
			String paths = currentResource.isWildcard() ? currentResource.getWildcardAbsolutePaths() : currentResource.getAbsolutePath();
			int idx = paths.indexOf(absolutePath);
			// "> 0" because the normal resource doesn't starts with "["
			if (idx > 0) {
				removeAbsolutePath((String)keys[i]);
			}
		}
	}

}
