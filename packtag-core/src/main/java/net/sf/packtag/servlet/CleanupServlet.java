/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 14.07.2007 - 21:32:13
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 17:19:06 $
 * Revision:      $Revision: 1.2 $
 * 
 * $Log: CleanupServlet.java,v $
 * Revision 1.2  2008/03/15 17:19:06  danielgalan
 * string externalization
 *
 * Revision 1.1  2007/08/27 22:36:49  danielgalan
 * Servlet, that deletes the packed files at startup
 *
 */
package net.sf.packtag.servlet;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.sf.packtag.util.ContextConfiguration;



/**
 * Can be used to clear the files created, when using cacheType file
 * 
 * @author Daniel Galán y Martins
 * @version $Revision: 1.2 $
 */
public class CleanupServlet extends HttpServlet {

	private static final long serialVersionUID = 8104391455171995581L;

	private static final String SLASH = "/";


	public void init(final ServletConfig config) throws ServletException {
		if (ContextConfiguration.isCachetypeFile(getServletContext())) {
			String path = ContextConfiguration.getCacheFilePath(getServletContext()) + SLASH;
			String realPath = getServletContext().getRealPath(SLASH) + path;
			File fileRealPath = new File(realPath);
			if (fileRealPath.exists()) {
				fileRealPath.delete();
				fileRealPath.mkdirs();
			}
		}
	}

}
