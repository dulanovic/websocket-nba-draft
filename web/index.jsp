<%-- 
    Document   : login
    Created on : May 5, 2017, 6:59:28 PM
    Author     : Korisnik
--%>

<%@page import="model.Korisnik"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>NBA Fatasy Liga 2017 - by Vidan Dulanović</title>

    <style>
        a:hover {
            color: #FF0000;
        }

        body {
            font-family: Cambria;
        }
    </style>
</head>
<body>
    <h1>Početna strana</h1>
    <%  HttpSession sesija = request.getSession();
        Korisnik k = (Korisnik) sesija.getAttribute("ulogovan_korisnik");
        if (k == null) { %>
    <a href="login.jsp">Strana za prijavu</a>
    <% } else { %>
    <p>${ulogovan_korisnik.ime} ${ulogovan_korisnik.prezime}</p>
    <a href="./logout">Odjava</a><br />
    <a href="./stranica/ajax">Ajax</a><br />
    <a href="./stranica/draft">Draft</a>
    <% }%>
</body>