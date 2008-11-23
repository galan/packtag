/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 13.11.2008 - 23:32:51
 * Last author:   $Author: danielgalan $
 * Last modified: $Date:$
 * Revision:      $Revision:$
 * 
 * $Log:$
 */
package net.sf.packtag.cache;

import java.util.Set;

import javax.servlet.ServletContext;




/**
 * A pluggable CacheProvider
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision:$
 */
public interface CacheProvider {

	public void init(ServletContext context);


	public Set getAbsolutePathKeys();


	public Set getMappedPathKeys();


	public Resource getResourceByAbsolutePath(final String absolutePath);


	public Resource getResourceByMappedPath(final String mappedPath);


	public boolean existResource(final String absolutePath);


	public void store(final Resource resource, final boolean clearDependingCombinedResources);


	public void clearCache();


	public void removeAbsolutePath(String absolutePath);


	public void removeMappedPath(String mappedPath);

}
