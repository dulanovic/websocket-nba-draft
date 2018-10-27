<%-- 
    Document   : login
    Created on : May 5, 2017, 7:01:31 PM
    Author     : Korisnik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h3>Prijava</h3>
        <h3>${poruka}</h3>
        <form method="POST" action="login">
            <table>
                <tr>
                    <td>
                        Korisnicko ime:
                    </td>
                    <td>
                        <input type="text" name="korisnickoIme" size="20" autofocus>
                    </td>
                </tr>
                <tr>
                    <td>
                        Korisnicka sifra:
                    </td>
                    <td>
                        <input type="password" name="korisnickaSifra" size="20">
                    </td>
                </tr>
                <tr rowspan="2">
                    <td>
                        <input type="submit" value="Prijavi se">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
