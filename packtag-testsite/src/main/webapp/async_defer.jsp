<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%-- <%@ taglib uri="http://www.galan.de/projects/packtag" prefix="pack" %> --%>
<%@ taglib uri="http://packtag.sf.net" prefix="pack" %>
<!DOCTYPE html>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>index.jsp</title>
	
	<pack:script src="js/dummy.js" async="true"/>
	<pack:script src="js/dummyCompress.js" minify="false" defer="true"/>
        
    <pack:script defer="true">
		<src>js/dummy.js</src>
		<src>js/dummyCompress.js</src>
	</pack:script>

	<pack:script src="js/dummyDisabled.js" enabled="false"/>
</head>

<body>
	hello<br/>
	hell
</body>

</html>
