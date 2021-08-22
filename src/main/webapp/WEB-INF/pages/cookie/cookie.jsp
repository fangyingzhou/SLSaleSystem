<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	welcome cookie

 <%
	 out.print("key"+request.getAttribute("key")+"<br/>"+"value:"+request.getAttribute("value"));
 %>
</body>
</html>


