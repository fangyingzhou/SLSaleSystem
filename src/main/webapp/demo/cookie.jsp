<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	welcome cookie
    ${key1}
    ${value1}

 <%
	 out.print("key"+request.getAttribute("key")+"<br/>"+"value:"+request.getAttribute("value"));
 %>
</body>
</html>


