/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Creation date: 12.03.2008 - 22:15:43
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/03/15 16:33:57 $
 * Revision:      $Revision: 1.1 $
 * 
 * $Log: AbstractFilter.java,v $
 * Revision 1.1  2008/03/15 16:33:57  danielgalan
 * GzipFilter vor dynamic resources (e.g. HTML)
 *
 */
package net.sf.packtag.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * class desciption. Purpose, functionality, etc..
 * 
 * @author  Daniel Galán y Martins
 * @version $Revision: 1.1 $
 */
public class AbstractFilter implements Filter {

	public void init(final FilterConfig config) throws ServletException {
	}


	public void destroy() {
	}


	public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;

		preChain(request, response);
		chain.doFilter(servletRequest, servletResponse);
		postChain(request, response);
	}


	protected void preChain(final HttpServletRequest request, final HttpServletResponse response) {
	}


	protected void postChain(final HttpServletRequest request, final HttpServletResponse response) {
	}

}
