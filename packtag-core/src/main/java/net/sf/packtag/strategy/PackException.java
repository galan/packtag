/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.strategy;

/**
 * Exception thrown when the strategy has trouble compressing the resource.
 *
 * @author Daniel Galán y Martins
 */
public class PackException extends Exception {

	private static final long serialVersionUID = -3665397358186587342L;


	public PackException(final String message, final Throwable cause) {
		super(message, cause);
	}


	public PackException(final String message) {
		super(message);
	}


	public PackException(final Throwable throwable) {
		super(throwable);
	}

}
