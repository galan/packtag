/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 *
 * Creation date: Feb 20, 2015 - 8:38:25 PM
 * Last author:   $Author: danielgalan $
 * Last modified: $Date:$
 * Revision:      $Revision:$
 *
 * $Log:$
 */
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
 * @author daniel
 */
public class Github5 extends TestCase {

	public void testBug() throws Exception {
		FileReader reader = new FileReader(new File("/home/daniel/Dropbox/dev/git/packtag/packtag-testsite/src/main/webapp/js/moment-2.9.0.min.js"));
		StringWriter sw = new StringWriter();
		JSMin min = new JSMin(reader, sw);
		min.jsmin();
		assertEquals(sw.toString().trim().length(), 24183);
	}


	public void testStrategy() throws Exception {
		FileInputStream fis = new FileInputStream(new File("/home/daniel/Dropbox/dev/git/packtag/packtag-testsite/src/main/webapp/js/moment-2.9.0.min.js"));
		JsminPackStrategy strategy = new JsminPackStrategy();
		String pack = strategy.pack(fis, Charset.forName("UTF-8"), null);
		assertEquals(pack.length(), 24183);
	}

}
