<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Enumeration"%>
<%@page import="java.nio.charset.Charset"%>
<%@page import="java.util.SortedMap"%>
<%@page import="java.util.Iterator"%>
<html>

<head>
	<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
	<title>debug.jsp</title>
</head>

<body>
Header:
	<table>
		<tr><td>HeaderName</td><td>HeaderValue</td></tr>
	<%
		Enumeration enumHeaders = request.getHeaderNames();
		while (enumHeaders.hasMoreElements()) {
			String headerName = (String)enumHeaders.nextElement();
			String value = request.getHeader(headerName);
			out.write("<tr><td>" + headerName + "</td><td>" + value + "</td></tr>");
		}
	%>
	</table>
	<br/>
	<br/>
	
	<table>
		<tr><td>Result</td><td>Method</td></tr>
	<%
	out.write("<tr><td>" + request.getLocalAddr() + "</td><td>request.getLocalAddr()</td></tr>");
	out.write("<tr><td>" + request.getLocalName() + "</td><td>request.getLocalName()</td></tr>");
	out.write("<tr><td>" + request.getLocalPort() + "</td><td>request.getLocalPort()</td></tr>");
	out.write("<tr><td>" + request.getPathInfo() + "</td><td>request.getPathInfo()</td></tr>");
	out.write("<tr><td>" + request.getPathTranslated() + "</td><td>request.getPathTranslated()()</td></tr>");
	out.write("<tr><td>" + request.getRemoteAddr() + "</td><td>request.getRemoteAddr()</td></tr>");
	out.write("<tr><td>" + request.getRemoteHost() + "</td><td>request.getRemoteHost()</td></tr>");
	out.write("<tr><td>" + request.getRequestURI() + "</td><td>request.getRequestURI()</td></tr>");
	out.write("<tr><td>" + request.getScheme() + "</td><td>request.getScheme()</td></tr>");
	out.write("<tr><td>" + request.getServerName() + "</td><td>request.getServerName()</td></tr>");
	%>
	</table>
	<br/>
	<br/>
	
	Supported Charsets:
	<table>
		<tr><td>Key</td><td>Value</td></tr>
	<%
		SortedMap ac = Charset.availableCharsets();
		Iterator iterKeys = ac.keySet().iterator();
		while (iterKeys.hasNext()) {
			String key = (String)iterKeys.next();
			out.write("<tr><td>" + key + "</td><td>" + Charset.forName(key).name() + "</td></tr>");
		}
	%>
	</table>
	
</body>

</html>
