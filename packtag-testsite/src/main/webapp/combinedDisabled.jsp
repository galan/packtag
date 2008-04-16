<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%-- <%@ taglib uri="http://www.galan.de/projects/packtag" prefix="pack" %> --%>
<%@ taglib uri="http://packtag.sf.net" prefix="pack" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>combinedDisabled.jsp</title>
	
	<pack:script src="js/dummy.js" minify="false"/>

	<pack:script enabled="false">
		<src>js/dummy.js</src>
		<src>js/dummyDisabled.js</src>
	</pack:script>

	<pack:style enabled="false">
		<src>style/dummy.css</src>
		<src>style/dummyDisabled.css</src>
	</pack:style>

</head>

<body>
	hello<br/>
	hell
</body>

</html>
