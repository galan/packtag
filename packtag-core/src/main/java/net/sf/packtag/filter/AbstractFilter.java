/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**
 * class desciption. Purpose, functionality, etc..
 *
 * @author  Daniel Galán y Martins
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
