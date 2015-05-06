/* Project pack:tag >> https://github.com/galan/packtag */
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
