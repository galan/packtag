/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.cache;

import net.sf.packtag.util.SafeLogger;



/**
 * Instantiates a CacheProvider Class
 *
 * @author  Daniel Galán y Martins
 */
public class CacheProviderFactory {

	public CacheProvider build(final Class providerClass) {
		CacheProvider provider = null;
		try {
			provider = (CacheProvider)providerClass.newInstance();
		}
		catch (Exception ex) {
			SafeLogger.error(getClass(), "CacheProvider could not be instantiated (Class:" + providerClass + ")", ex);
		}
		return provider;
	}

}
