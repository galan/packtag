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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import net.sf.packtag.util.CharsetUtil;



public class GzipResponseWrapper extends HttpServletResponseWrapper {

	protected HttpServletResponse origResponse = null;
	protected ServletOutputStream stream = null;
	protected PrintWriter writer = null;


	public GzipResponseWrapper(final HttpServletResponse response) {
		super(response);
		origResponse = response;
	}


	public ServletOutputStream createOutputStream() throws IOException {
		return (new GzipResponseStream(origResponse));
	}


	public void finishResponse() {
		try {
			if (writer != null) {
				writer.close();
			}
			else {
				if (stream != null) {
					stream.close();
				}
			}
		}
		catch (IOException e) {
		}
	}


	public void flushBuffer() throws IOException {
		stream.flush();
	}


	public ServletOutputStream getOutputStream() throws IOException {
		if (writer != null) {
			throw new IllegalStateException("getWriter() has already been called!");
		}

		if (stream == null) {
			stream = createOutputStream();
		}
		return (stream);
	}


	public PrintWriter getWriter() throws IOException {
		if (writer != null) {
			return (writer);
		}

		if (stream != null) {
			throw new IllegalStateException("getOutputStream() has already been called!");
		}

		stream = createOutputStream();
		writer = new PrintWriter(new OutputStreamWriter(stream, CharsetUtil.UTF8));
		return (writer);
	}


	public void setContentLength(final int length) {
	}
}
