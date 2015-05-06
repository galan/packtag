/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.strategy;

import java.io.InputStream;
import java.nio.charset.Charset;



/**
 * A Strategy has to implement this Interface, the algorithm needs to return the
 * packed resource.
 *
 * @author Daniel Galán y Martins
 */
public interface PackStrategy {

	/**
	 * A algorithm returns a compressed resource from the Stream. Called once,
	 * when the resource is requested the first time.
	 *
	 * When providing the path param the URLs will be re-written to absolute
	 * paths in CSS resources (of course - if implemented in the configured
	 * PackStrategy). This will ensure that background images defined in CSS
	 * files will still work when re-mapping to a virtual path. PackStragegys
	 * for JS files might not need this. At least the logic for replacing the
	 * URLs is far more complicated.
	 *
	 * @param resourceAsStream The requested resource which should be compressed
	 * @param charset The charset of the delivered Stream
	 * @param path
	 * @return The compressed resource
	 * @throws PackException On errors during processing/minificate the resource
	 */
	public String pack(InputStream resourceAsStream, Charset charset, String path) throws PackException;

}
