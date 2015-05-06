<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%-- <%@ taglib uri="http://www.galan.de/projects/packtag" prefix="pack" %> --%>
<%@ taglib uri="http://packtag.sf.net" prefix="pack" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>github issue #5</title>
	
	
	<pack:script minify="false">
		<src>js/moment-2.9.0.min.js</src>
	</pack:script>
	
</head>

<body>
	<a href="https://github.com/galan/packtag/issues/5">github #5</a><br/>
	<p id="time">?</p>
	<script>
		document.getElementById('time').innerHTML = moment("20111031", "YYYYMMDD").fromNow();
	</script>
	
</body>

</html>
