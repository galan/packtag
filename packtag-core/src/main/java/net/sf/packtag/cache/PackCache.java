/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 12.04.2007 - 21:46:36
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2007/11/12 22:57:24 $
 * Revision:      $Revision: 1.3 $
 * 
 * $Log: PackCache.java,v $
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

/**
 * Singleton to the Cache
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.3 $
 */
public class PackCache {

	private static PackCache instance;
	private final ResourceCache resourceCache = new ResourceCache();


	private static PackCache getInstance() {
		if (instance == null) {
			synchronized (PackCache.class) {
				if (instance == null) {
					instance = new PackCache();
				}
			}
		}
		return instance;
	}


	private ResourceCache getResourceCache() {
		return resourceCache;
	}


	public static Resource getResourceByAbsolutePath(final String absolutePath) {
		return getInstance().getResourceCache().getResourceByAbsolutePath(absolutePath);
	}


	public static Resource getResourceByMappedPath(final String mappedPath) {
		return getInstance().getResourceCache().getResourceByMappedPath(mappedPath);
	}


	public static boolean existResource(final String absolutePath) {
		return getInstance().getResourceCache().existResource(absolutePath);
	}


	public static void store(final Resource resource, final boolean clearDependingCombinedResources) {
		getInstance().getResourceCache().store(resource, clearDependingCombinedResources);
	}


	public static void clearCache() {
		getInstance().getResourceCache().clearCache();
	}

}
