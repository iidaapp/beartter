<%@page import="com.iidaapp.beartter.TestWebServlet"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%
    boolean isPOST = request.getMethod().toLowerCase().equals("post");
    if (isPOST) {
    	TestWebServlet.sendMessage(request.getParameter("msg"));
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>WebSocketDemo Control</title>
    </head>
    <body>
        <%= isPOST ? "Message was sent." : "" %>
        <form action="" method="post">
            <input type="text" name="msg">
            <input type="submit" name="submit" value="Send">
        </form>
    </body>
</html>