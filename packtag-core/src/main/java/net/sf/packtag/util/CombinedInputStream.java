/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 15.04.2007 - 18:27:26
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/02/10 20:03:21 $
 * Revision:      $Revision: 1.4 $
 * 
 * $Log: CombinedInputStream.java,v $
 * Revision 1.4  2008/02/10 20:03:21  danielgalan
 * Fix for IE - if after a closing bracket a var keyword is, IE can't interpret this, eg "varx={}var y ={}" mus be seperated: "varx={}\nvar y ={}"
 *
 * Revision 1.3  2007/05/02 21:38:38  danielgalan
 * alias to name
 *
 * Revision 1.2  2007/05/02 21:29:18  danielgalan
 * last fixes for 2.0, attribute media
 *
 * Revision 1.1  2007/04/22 19:04:24  danielgalan
 * pack.tag moved from subversion to good old CVS
 */
package net.sf.packtag.util;

import java.io.IOException;
import java.io.InputStream;



/**
 * Reads multiple Inputstreams as one
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.4 $
 */
public class CombinedInputStream extends InputStream {

	/** The current Stream to read from */
	private int current;

	boolean isNextLineFeed = false;

	/** The Streams to combine */
	private final InputStream[] streams;


	/**
	 * Constructs an combined InputStream, that reads from array stream per stream, till the last stream
	 * 
	 * @param streams All Streams that will be combined
	 */
	public CombinedInputStream(final InputStream[] streams) {
		current = 0;
		this.streams = streams;
	}


	/**
	 * Reads from the list of streams, when the last stream is over, -1 will be returned (as usual)
	 */
	public int read() throws IOException {
		if (isNextLineFeed) {
			isNextLineFeed = false;
			return '\n';
		}
		int i = -1;
		if (current < streams.length) {
			i = streams[current].read();
			if (i == -1) {
				isNextLineFeed = true;
				// the current stream has been finished, use the next one
				current++;
				return read();
			}
		}
		return i;
	}


	/** Closes all streams */
	public void close() throws IOException {
		for (int i = 0; i < streams.length; i++) {
			streams[i].close();
		}
	}


	/** Is there more data to read */
	public int available() throws IOException {
		return current < streams.length ? streams[current].available() : 0;
	}
}
