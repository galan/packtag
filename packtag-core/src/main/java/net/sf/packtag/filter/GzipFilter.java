/*
 * Copyright 2003 Jayson Falkner (jayson@jspinsider.com)
 * This code is from "Servlets and JavaServer pages; the J2EE Web Tier",
 * http://www.jspbook.com. You may freely use the code both commercially
 * and non-commercially. If you like the code, please pick up a copy of
 * the book and help support the authors, development of more free code,
 * and the JSP/Servlet/J2EE community.
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

import net.sf.packtag.util.HttpHeader;
import net.sf.packtag.util.RequestUtil;



/**
 * Gzips the content of the filtered Request
 *
 * http://www.onjava.com/pub/a/onjava/2003/11/19/filters.html
 *
 * @author Daniel Galán y Martins
 */
public class GzipFilter implements Filter {

	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest)req;
			HttpServletResponse response = (HttpServletResponse)res;
			response.setHeader(HttpHeader.BRANDING_HEADER, HttpHeader.BRANDING_VALUE);
			if(request.getHeader(HttpHeader.ACCEPTED_ENCODING) != null) {
				response.setHeader(HttpHeader.VARY, HttpHeader.ACCEPTED_ENCODING);
			}
			if (RequestUtil.isGZipSupported(request)) {
				GzipResponseWrapper wrappedResponse = new GzipResponseWrapper(response);
				chain.doFilter(req, wrappedResponse);
				wrappedResponse.finishResponse();
			}
			else {
				chain.doFilter(req, res);
			}
		}
		else {
			chain.doFilter(req, res);
		}
	}


	public void init(final FilterConfig filterConfig) {
	}


	public void destroy() {
	}

}
