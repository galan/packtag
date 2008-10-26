/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 07.02.2008 - 21:36:11
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 21:40:03 $
 * Revision:      $Revision: 1.2 $
 * 
 * $Log: SafeLogger.java,v $
 * Revision 1.2  2008/03/15 21:40:03  danielgalan
 * Name correction...
 *
 * Revision 1.1  2008/02/10 20:59:49  danielgalan
 * SafeLogger
 *
 */
package net.sf.packtag.util;

/**
 * Logs Events to the available Logger, Log4j, elswise System.err.
 * 
 * @author  Daniel Gal�n y Martins
 * @version $Revision: 1.2 $
 */
public class SafeLogger {

	private static Boolean log4jAvailable = null;
	private static Boolean commonsLoggingAvailable = null;


	/** Returns true if the classes from the Apache Log4j library is available */
	protected static boolean isLog4jAvailable() {
		if (log4jAvailable == null) {
			synchronized (SafeLogger.class) {
				if (log4jAvailable == null) {
					try {
						log4jAvailable = new Boolean(Class.forName("org.apache.log4j.Logger") != null);
					}
					catch (ClassNotFoundException e) {
						log4jAvailable = Boolean.FALSE;
					}
				}
			}
		}
		return log4jAvailable.booleanValue();
	}


	/** Returns true if the classes from the Apache Commons Logging library is available */
	protected static boolean isCommonsLoggingAvailable() {
		if (commonsLoggingAvailable == null) {
			synchronized (SafeLogger.class) {
				if (commonsLoggingAvailable == null) {
					try {
						commonsLoggingAvailable = new Boolean(Class.forName("org.apache.commons.logging") != null);
					}
					catch (ClassNotFoundException e) {
						commonsLoggingAvailable = Boolean.FALSE;
					}
				}
			}
		}
		return commonsLoggingAvailable.booleanValue();
	}


	public static void error(final Class sender, final String message) {
		error(sender, message, null);
	}


	public static void error(final Class sender, final String message, final Exception exception) {
		if (isLog4jAvailable()) {
			org.apache.log4j.Logger.getLogger(sender).error(message, exception);
		}
		else if (isCommonsLoggingAvailable()) {
			org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(sender);
			log.error(message, exception);
		}
		else {
			System.err.println(sender.getName() + ": " + message);
			if (exception != null) {
				exception.printStackTrace();
			}
		}
	}

}
