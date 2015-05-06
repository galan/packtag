/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.bugs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.StringWriter;
import java.nio.charset.Charset;

import junit.framework.TestCase;
import net.sf.packtag.implementation.JSMin;
import net.sf.packtag.implementation.JsminPackStrategy;



/**
 * Test for Github bug #5 - https://github.com/galan/packtag/issues/5
 *
 * @author Daniel Galán y Martins
 */
public class Github5 extends TestCase {

	public void testBug() throws Exception {
		FileReader reader = new FileReader(new File("packtag/packtag-testsite/src/main/webapp/js/moment-2.9.0.min.js"));
		StringWriter sw = new StringWriter();
		JSMin min = new JSMin(reader, sw);
		min.jsmin();
		assertEquals(sw.toString().trim().length(), 24183);
	}


	public void testStrategy() throws Exception {
		FileInputStream fis = new FileInputStream(new File("packtag/packtag-testsite/src/main/webapp/js/moment-2.9.0.min.js"));
		JsminPackStrategy strategy = new JsminPackStrategy();
		String pack = strategy.pack(fis, Charset.forName("UTF-8"), null);
		assertEquals(pack.length(), 24183);
	}

}
