/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 04.11.2007 - 19:47:48
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 21:37:07 $
 * Revision:      $Revision: 1.2 $
 * 
 * $Log: URIUtilsTest.java,v $
 * Revision 1.2  2008/03/15 21:37:07  danielgalan
 * Name correction..
 *
 * Revision 1.1  2007/11/06 23:11:34  danielgalan
 * Testase for URI Handling
 *
 */
package net.sf.packtag.test;

import junit.framework.TestCase;
import net.sf.packtag.util.URIUtils;



/**
 * class desciption. Purpose, functionality, etc..
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.2 $
 */
public class URIUtilsTest extends TestCase {

	public void testSubdirs() {
		assertEquals("/dummyCompress.css", URIUtils.cleanRelativePath("/dummyCompress.css"));
		assertEquals("/packtag/style/dummyCompress.css", URIUtils.cleanRelativePath("/packtag/style/dummyCompress.css"));
		assertEquals("/packtag/style/dummyCompress.css", URIUtils.cleanRelativePath("/packtag/subdir/../style/dummyCompress.css"));
		assertEquals("/packtag/dummyCompress.css", URIUtils.cleanRelativePath("/packtag/style/../dummyCompress.css"));
		assertEquals("/packtag/somedir/anotherdir/dummyCompress.css", URIUtils
			.cleanRelativePath("/packtag/style/../somedir/somewhere/../anotherdir/dummyCompress.css"));
		assertEquals("/packtag/anotherdir/dummyCompress.css", URIUtils.cleanRelativePath("/packtag/style/blabla/../../anotherdir/dummyCompress.css"));
		assertEquals("/style/dummyCompress.css", URIUtils.cleanRelativePath("/packtag/../style/dummyCompress.css"));
	}


	public void testCurrentDirs() {
		assertEquals("/style/dummyCompress.css", URIUtils.cleanRelativePath("/style/./dummyCompress.css"));
		assertEquals("/style/dummyCompress.css", URIUtils.cleanRelativePath("/./style/././dummyCompress.css"));
		assertEquals("/dummyCompress.css", URIUtils.cleanRelativePath("/./dummyCompress.css"));
	}


	public void testAbsolutePath() {
		assertEquals("/packtag/style/dummyCompress.css", URIUtils.cleanRelativePath("/packtag/./style/./blablup/../dummyCompress.css"));
	}


	public void testSubdirDoesNotExists() {
		assertEquals("/style/dummyCompress.css", URIUtils.cleanRelativePath("/../style/dummyCompress.css"));
	}

}
