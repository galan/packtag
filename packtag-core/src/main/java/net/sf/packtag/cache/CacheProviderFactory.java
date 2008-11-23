/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 15.11.2008 - 13:10:00
 * Last author:   $Author: danielgalan $
 * Last modified: $Date:$
 * Revision:      $Revision:$
 * 
 * $Log:$
 */
package net.sf.packtag.cache;

import net.sf.packtag.util.SafeLogger;



/**
 * Instantiates a CacheProvider Class
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision:$
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
