/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.strategy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;



/**
 * A super Strategy with supporting methods.
 *
 * @author Daniel Galán y Martins
 */
public abstract class AbstractPackStrategy implements PackStrategy {

	protected static final String LINE_SEPARATOR = System.getProperty("line.separator");


	protected String resourceToString(final InputStream resourceAsStream, final Charset charset) throws PackException {
		StringBuffer result = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream, charset));
		try {
			boolean firstLine = true;
			String line = br.readLine();
			while(line != null) {
				if (firstLine) {
					firstLine = false;
				}
				else {
					result.append(LINE_SEPARATOR);
				}
				result.append(line);
				line = br.readLine();
			}
			br.close();
		}
		catch (Exception ex) {
			throw new PackException(ex);
		}
		return result.toString();
	}

}
