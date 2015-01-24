<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%-- <%@ taglib uri="http://www.galan.de/projects/packtag" prefix="pack" %> --%>
<%@ taglib uri="http://packtag.sf.net" prefix="pack" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>combined.jsp</title>
	
	<%-- <pack:script src="js/dummy.js"/> --%>
	<pack:script src="js/dummyCompress.js" minify="false"/>

	<pack:script prefix="dummies">
		<src>js/dummy.js</src>
		<src>js/dummyCompress.js</src>
	</pack:script>

	<pack:style prefix="styles">
		<src>style/dummy.css</src>
		<src>style/dummyDisabled.css</src>
	</pack:style>
	
</head>

<body>
	hello<br/>
	hell<br/>

	<div id="testX">
	</div>
	
	<script type="text/javascript">
	xPackage.test();
	</script>
</body>

</html>
