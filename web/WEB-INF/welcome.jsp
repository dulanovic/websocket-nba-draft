<%-- 
    Document   : hidden
    Created on : May 5, 2017, 7:12:41 PM
    Author     : Korisnik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Secret</title>
    </head>
    <body>
        <h3>${ulogovan_korisnik.ime} ${ulogovan_korisnik.prezime}</h3>
        <a href="./logout">Logout</a><br />
        <a href="./stranica/ajax">Ajax test</a><br />
        <a href="./stranica/draft">Draft</a>
    </body>
</html>
