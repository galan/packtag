/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2007/04/22 19:04:23 $
 * Revision:      $Revision: 1.1 $
 * 
 * $Log: PackException.java,v $
 * Revision 1.1  2007/04/22 19:04:23  danielgalan
 * pack.tag moved from subversion to good old CVS
 *
 */
package net.sf.packtag.strategy;

/**
 * Exception thrown when the strategy has trouble compressing the resource.
 * 
 * @author Daniel Galán y Martins
 * @version $Revision: 1.1 $
 */
public class PackException extends Exception {

	private static final long serialVersionUID = -3665397358186587342L;


	public PackException(String message, Throwable cause) {
		super(message, cause);
	}


	public PackException(String message) {
		super(message);
	}


	public PackException(Throwable throwable) {
		super(throwable);
	}

}
