/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 *
 * Creation date: 17.02.2007 - 23:49:17
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2007/10/12 21:50:40 $
 * Revision:      $Revision: 1.4 $
 *
 * $Log: PackTestCase.java,v $
 * Revision 1.4  2007/10/12 21:50:40  danielgalan
 * default Charset solution
 *
 * Revision 1.3  2007/10/12 20:57:02  danielgalan
 * Files before branch
 *
 * Revision 1.2  2007/09/23 14:37:31  danielgalan
 * Added charset support
 *
 * Revision 1.1  2007/04/22 19:04:23  danielgalan
 * pack.tag moved from subversion to good old CVS
 *
 */
package net.sf.packtag.test;

import java.io.InputStream;
import java.nio.charset.Charset;

import junit.framework.TestCase;
import net.sf.packtag.strategy.AbstractPackStrategy;
import net.sf.packtag.strategy.PackException;
import net.sf.packtag.util.CharsetUtil;



/**
 * class desciption. Purpose, functionality, etc..
 *
 * @author Daniel Galán y Martins
 * @version $Revision: 1.4 $
 */
public abstract class AbstractPacktagTestCase extends TestCase {

	protected String resourceToString(final InputStream resourceAsStream) throws PackException {
		class MockStrategy extends AbstractPackStrategy {

			public String pack(final InputStream ras, final Charset charset, final String path) throws PackException {
				return null;
			}


			// Make it public
			public String resourceToString(final InputStream ras) throws PackException {
				return super.resourceToString(ras, getDefaultCharset());
			}
		}
		MockStrategy mock = new MockStrategy();
		return mock.resourceToString(resourceAsStream);
	}


	protected Charset getDefaultCharset() {
		return new CharsetUtil().getDefaultCharset();
	}
}
