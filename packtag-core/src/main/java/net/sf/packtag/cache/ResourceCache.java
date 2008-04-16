/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 15.04.2007 - 11:02:24
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 16:38:51 $
 * Revision:      $Revision: 1.4 $
 * 
 * $Log: ResourceCache.java,v $
 * Revision 1.4  2008/03/15 16:38:51  danielgalan
 * clean depencies for wildcard resources
 *
 * Revision 1.3  2007/11/12 22:57:24  danielgalan
 * renamed sqp to absolutePath and fqp to mappedPath
 *
 * Revision 1.2  2007/05/02 21:38:37  danielgalan
 * alias to name
 *
 * Revision 1.1  2007/04/22 19:04:21  danielgalan
 * pack.tag moved from subversion to good old CVS
 *
 */
package net.sf.packtag.cache;

import java.util.Hashtable;



/**
 * Contains the Minifed and gzipped Resources
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.4 $
 */
public class ResourceCache {

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


	private void clearDependingCombinedResources(final Resource resource) {
		String absolutePath = resource.getAbsolutePath();
		Object[] keys = resourcesAbsolutePath.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			Resource currentResource = (Resource)resourcesAbsolutePath.get(keys[i]);
			// Difference between combined and wildcard resources
			String paths = currentResource.isWildcard() ? currentResource.getWildcardAbsolutePaths() : currentResource.getAbsolutePath();
			int idx = paths.indexOf(absolutePath);
			// "> 0" because the normal resource doesn't starts with "["
			if (idx > 0) {
				resourcesAbsolutePath.remove(keys[i]);
			}
		}
	}

}
