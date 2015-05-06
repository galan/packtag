/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.cache;

import java.util.Set;

import javax.servlet.ServletContext;



/**
 * A pluggable CacheProvider
 *
 * @author  Daniel Galán y Martins
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
