/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.util;

/**
 * Logs Events to the available Logger, Log4j, elswise System.err.
 *
 * @author  Daniel Galán y Martins
 */
public class SafeLogger {


	public static void error(final Class sender, final String message) {
		error(sender, message, null);
	}


	public static void error(final Class sender, final String message, final Exception exception) {
		// hard-wire log4j2 api
		org.apache.logging.log4j.LogManager.getLogger(sender).error(message, exception);
	}

}
