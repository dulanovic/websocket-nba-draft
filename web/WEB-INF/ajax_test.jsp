<%-- 
    Document   : ajax_test
    Created on : May 7, 2017, 1:19:23 AM
    Author     : Korisnik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ajax Test Page</title>
        
        <style>
            body {
                font-family: Cambria;
            }
        </style>

        <script type="text/javascript">
            var xmlHttp;

            function getXmlHttpRequest() {
                var xmlHttp = null;
                try {
                    xmlHttp = new XMLHttpRequest();
                } catch (e) {
                    try {
                        xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
                    } catch (e) {
                        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                }
                return xmlHttp;
            }

            function ajaxIgraci() {
                var unos = document.getElementById("ajax").value;
                if (unos == "") {
                    ispis.style.display = "none";
                    return;
                }
                xmlHttp = getXmlHttpRequest();
                if (xmlHttp == null) {
                    alert("Vaš browser ne podržava AJAX HTTP zahteve!");
                    return;
                }

                var adresa = "../ajax?kriterijum=" + unos;
                xmlHttp.open("GET", adresa, true);
                xmlHttp.send(null);
                xmlHttp.onreadystatechange = function () {
                    var ispis = document.getElementById("ispis");
                    ispis.style.display = "block";
                    ispis.innerHTML = xmlHttp.responseText;
                };
            }
        </script>

    </head>
    <body>
        <input type="text" id="ajax" size="30" autofocus onkeyup="ajaxIgraci()"><br />
        <div id="ispis" style="display: none;"></div>
    </body>
</html>
