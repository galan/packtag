/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 13.11.2008 - 23:49:49
 * Last author:   $Author: danielgalan $
 * Last modified: $Date:$
 * Revision:      $Revision:$
 * 
 * $Log:$
 */
package net.sf.packtag.cache.provider;

import javax.servlet.ServletContext;

import net.sf.packtag.cache.CacheProvider;
import net.sf.packtag.cache.Resource;



/**
 * Basic CacheProvider functions
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision:$
 */
public abstract class AbstractCacheProvider implements CacheProvider {

	public void init(final ServletContext context) {
	}


	protected void clearDependingCombinedResources(final Resource resource) {
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
