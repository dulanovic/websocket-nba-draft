<%-- 
    Document   : draft
    Created on : May 7, 2017, 5:37:15 PM
    Author     : Korisnik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Draft</title>

        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

        <style>
            body {
                font-family: Cambria;
            }

            table {
                text-align: center;
            }

            .slikaIgrac {
                width: 200px;
            }

            .timLogo {
                width: 30px;
            }

            .zaIzbor {
                border: 2px solid #FFFFFF;
            }

            #tabelaIgraci .ui-selecting {
                background: #FECA40;
            }

            #tabelaIgraci .ui-selected {
                background: #F39814;
                color: white;
                border: 2px solid #000000;
            }

            #izabraniIgraci table {
                width: 1000px;
            }

            #vreme {
                position: fixed;
                font-size: 30px;
                color: #FF0000;
                z-index: 1;
                border: 2px solid #FF0000;
                width: 30px;
            }
        </style>

        <script type="text/javascript">
            var websocket = new WebSocket("ws://localhost:8080/DraftTest/fantasy/draft/${ulogovan_korisnik.liga.ligaId}/${ulogovan_korisnik.korisnickoIme}");
            websocket.onmessage = function processMessage(message) {
                if (message != null) {
                    if (message.data.startsWith("<table>")) {
                        document.getElementById("draft").innerHTML = message.data;
                    } else if (message.data.startsWith("Ulogovao se")) {
                        document.getElementById("poruke").value += message.data + "\n";
                    } else if (message.data.startsWith("Izabran je")) {
                        document.getElementById("poruke").value += message.data + "\n";
                    } else if (message.data.startsWith("Ti si na redu")) {
                        document.getElementById("provera").disabled = false;
                        document.getElementById("sistemskaPoruka").innerHTML = "";
                        vreme();
                        document.getElementById("vreme").style.display = "block";
                    } else if (message.data.startsWith("Cekaj")) {
                        document.getElementById("provera").disabled = true;
                        document.getElementById("sistemskaPoruka").innerHTML = "";
                        document.getElementById("vreme").style.display = "none";
                    } else if (message.data.startsWith("Trenutni")) {
                        var trenutniPik = message.data.substr(17);
                        var trenutnaRunda = Math.ceil(trenutniPik / 4);
                        document.getElementById("trenutnaRunda").value = trenutnaRunda;
                        document.getElementById("trenutniPik").value = trenutniPik;
                    } else if (message.data.startsWith("Nisu prisutni")) {
                        document.getElementById("provera").disabled = true;
                        document.getElementById("sistemskaPoruka").innerHTML = "<h4>" + message.data + "</h4>";
                    }
                }
            };

            function posaljiPodatke() {
                var igrac_id = document.getElementById("igrac_id").value;
                var korisnik_id = document.getElementById("korisnik_id").value;
                var liga_id = document.getElementById("liga_id").value;
                var poruka = "{'igrac_id': '" + igrac_id + "', 'korisnik_id': '" + korisnik_id + "', 'liga_id': '" + liga_id + "'}";
                websocket.send(poruka);
                zaustaviVreme();

                var izabraniIgrac = document.getElementById("igrac_id-" + igrac_id).innerHTML;
                var redIzabraniIgraci = document.getElementById("redIzabraniIgraci");
                redIzabraniIgraci.innerHTML += izabraniIgrac;
                ajaxSelectPozicija();
            }


            var timer;

            function vreme() {
                var vreme = 40;
                timer = setInterval(function () {
                    document.getElementById("vreme").innerHTML = vreme;
                    if (--vreme < 0) {
                        document.getElementById("vreme").innerHTML = "Isteklo vreme!";
                        zaustaviVreme();
                        websocket.send("Isteklo vreme");
                    }
                }, 1000);
            }

            function zaustaviVreme() {
                clearInterval(timer);
            }

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

            function ajaxSelectPozicija() {
                var ligaId = document.getElementById("liga_id").value;
                var select = document.getElementById("izborPozicija");
                var unos = select.options[select.selectedIndex].value;

                xmlHttp = getXmlHttpRequest();
                if (xmlHttp == null) {
                    alert("Vaš browser ne podržava AJAX HTTP zahteve!");
                    return;
                }

                var adresa = "../ajaxdraft?liga=" + ligaId + "&kriterijum=" + unos;
                xmlHttp.open("GET", adresa, true);
                xmlHttp.send(null);
                xmlHttp.onreadystatechange = function () {
                    var tabela = document.getElementById("tabelaIgraci");
                    tabela.innerHTML = xmlHttp.responseText;
                };
            }

            $(function () {
                $("#tabelaIgraci").selectable({
                    filter: ".zaIzbor",
                    selected: function (event, ui) {
                        var id = $(ui.selected).attr("id");
                        $("#igrac_id").val(id.substr(9));
                    }
                });
            });
        </script>

    </head>
    <body>
        <h3>DRAFT</h3>
        <div id="brojac">
            Trenutna runda: <input type="text" id="trenutnaRunda" value="" size="10" readonly>
            Trenutni pik: <input type="text" id="trenutniPik" value="" size="10" readonly>
            <p id="vreme" style="display: none;"></p>
        </div><br />
        <div id="sistemskaPoruka">

        </div><br />
        <input type="text" id="igrac_id" value="" size="5" readonly>
        <input type="text" id="korisnik_id" value="${ulogovan_korisnik.korisnikId}" size="5" readonly>
        <input type="text" id="liga_id" value="${ulogovan_korisnik.liga.ligaId}" size="5" readonly>
        <input type="submit" value="Posalji podatke" id="provera" onclick="posaljiPodatke()"><br />
        <select id="izborPozicija" onchange="ajaxSelectPozicija()">
            <option selected disabled>Izaberite poziciju</option>
            <option value="svi">Svi</option>
            <option value="PG">Plejmejker</option>
            <option value="SG">Bek</option>
            <option value="SF">Krilo</option>
            <option value="PF">Krilni centar</option>
            <option value="C">Centar</option>
        </select>
        <div id="tabelaIgraci">

        </div>
        <textarea id="poruke" cols="30" rows="10" readonly></textarea>
        <div id="draft">

        </div>
        <div id="izabraniIgraci">
            <table>
                <tr id="redIzabraniIgraci">

                </tr>
            </table>
        </div>
        <h3>${ulogovan_korisnik.korisnickoIme}</h3>
        <h3>${ulogovan_korisnik.liga.nazivLige}</h3>
    </body>
</html>
