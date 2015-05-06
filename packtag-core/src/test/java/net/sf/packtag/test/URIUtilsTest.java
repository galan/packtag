/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.test;

import junit.framework.TestCase;
import net.sf.packtag.util.URIUtils;



/**
 * class desciption. Purpose, functionality, etc..
 *
 * @author  Daniel Galán y Martins
 */
public class URIUtilsTest extends TestCase {

	public void testSubdirs() {
		assertEquals("/dummyCompress.css", URIUtils.cleanRelativePath("/dummyCompress.css"));
		assertEquals("/packtag/style/dummyCompress.css", URIUtils.cleanRelativePath("/packtag/style/dummyCompress.css"));
		assertEquals("/packtag/style/dummyCompress.css", URIUtils.cleanRelativePath("/packtag/subdir/../style/dummyCompress.css"));
		assertEquals("/packtag/dummyCompress.css", URIUtils.cleanRelativePath("/packtag/style/../dummyCompress.css"));
		assertEquals("/packtag/somedir/anotherdir/dummyCompress.css",
			URIUtils.cleanRelativePath("/packtag/style/../somedir/somewhere/../anotherdir/dummyCompress.css"));
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
