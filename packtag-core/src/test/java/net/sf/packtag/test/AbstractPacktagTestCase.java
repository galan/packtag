/* Project pack:tag >> https://github.com/galan/packtag */
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
