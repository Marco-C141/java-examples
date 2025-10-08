<%-- 
    Document   : index
    Created on : Oct 7, 2025, 10:55:02 PM
    Author     : lowlight
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.mycompany.impresoraweb.domain.Printer" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% 
            Printer printer = Printer.getInstance(); 
            printer.setName("Canon");
            Printer printer1 = Printer.getInstance();
            printer1.setName("HP");
        %>
        <h1>El nombre de la impresora es: <%= printer1.getName() %></h1>
    </body>
</html>
