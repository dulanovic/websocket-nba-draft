package websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.DatabaseBroker;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import model.Korisnik;

@ServerEndpoint(value = "/fantasy/draft/{ligaId}/{korisnickoIme}")
public class DraftServer {

    private static Map<String, List<Session>> sveLige = Collections.synchronizedMap(new HashMap<>());
    private static Map<String, List<Korisnik>> redosled = Collections.synchronizedMap(new HashMap<>());
    private static Map<String, String> logovi = Collections.synchronizedMap(new HashMap<>());
    private static Map<String, Integer> pikEvidencija = Collections.synchronizedMap(new HashMap<>());

    @OnOpen
    public void open(@PathParam("ligaId") String ligaId, @PathParam("korisnickoIme") String korisnickoIme, Session session) throws IOException {
        session.getUserProperties().put("korisnickoIme", korisnickoIme);

        if (sveLige.containsKey(ligaId)) {
            if (!sveLige.get(ligaId).contains(session)) {
                sveLige.get(ligaId).add(session);
            }
        } else {
            sveLige.put(ligaId, new ArrayList<>());
            sveLige.get(ligaId).add(session);
        }
        System.out.println("Dosao -> " + session.getUserProperties().get("korisnickoIme") + " ---> " + session.getId());

        int brojacUkupno = 0;
        for (List<Session> lista : sveLige.values()) {
            brojacUkupno += lista.size();
            for (Session s : lista) {
                System.out.println(s.getUserProperties().get("korisnickoIme"));
            }
            System.out.println("\n\n");
        }
        List<Session> listaLiga = sveLige.get(ligaId);

        System.out.println("Broj sesija ukupno: " + brojacUkupno + "\n" + "Broj sesija lige " + ligaId + ": " + sveLige.get(ligaId).size());

        for (Session s : sveLige.get(ligaId)) {
            s.getBasicRemote().sendText("Ulogovao se korisnik: " + korisnickoIme);
        }

        if (sveLige.get(ligaId).size() == 4) {
            pocniDraft(ligaId);
        } else {
            for (Session s : sveLige.get(ligaId)) {
                s.getBasicRemote().sendText("Nisu prisutni svi igraci, i draft ne moze jos da pocne");
            }
        }

    }

    @OnMessage
    public void message(@PathParam("ligaId") String ligaId, @PathParam("korisnickoIme") String korisnickoIme, String poruka, Session session) throws IOException {
        System.out.println(poruka);
        ObjectMapper om = new ObjectMapper();
        int pik = pikEvidencija.get(ligaId);

        if (poruka.startsWith("Isteklo")) {
            Korisnik k = redosled.get(ligaId).get(pikEvidencija.get(ligaId) - 1);
            redosled.get(ligaId).add(k);
        } else {
            JsonNode json = om.readTree(poruka);
            int igracId = json.get("igrac_id").asInt();
            int korisnikId = json.get("korisnik_id").asInt();
            int ligaid = json.get("liga_id").asInt();
            int runda = (int) Math.ceil(pik / 4.00);
            DatabaseBroker.getInstance().sacuvajPik(runda, pik, igracId, korisnikId, ligaid);

            for (Session s : sveLige.get(ligaId)) {
                s.getBasicRemote().sendText("Izabran je igrac: " + igracId + ", od strane korisnika: " + korisnikId + " u ligi: " + ligaid);
            }
        }

        pik++;
        pikEvidencija.put(ligaId, pik);

        int indeksSledeceg = pik - 1;

        System.out.println("indeksSledeceg ---> " + indeksSledeceg);
        String korisnickoImeSledeceg = redosled.get(ligaId).get(indeksSledeceg).getKorisnickoIme();
        for (Session s : sveLige.get(ligaId)) {
            if (s.getUserProperties().get("korisnickoIme").equals(korisnickoImeSledeceg)) {
                s.getBasicRemote().sendText("Ti si na redu!");
                s.getBasicRemote().sendText("Trenutni pik ---> " + indeksSledeceg);
            } else {
                s.getBasicRemote().sendText("Cekaj!");
                s.getBasicRemote().sendText("Trenutni pik ---> " + indeksSledeceg);
            }
        }

        System.out.println("Lista(duzina) ---> " + redosled.get(ligaId).size() + ", zadnji element ---> " + redosled.get(ligaId).get(redosled.get(ligaId).size() - 1));
    }

    @OnClose
    public void close(@PathParam("ligaId") String ligaId, @PathParam("korisnickoIme") String korisnickoIme, Session session) {
        sveLige.get(ligaId).remove(session);
        System.out.println("Otisao -> " + session.getId());
    }

    private void pocniDraft(@PathParam("ligaId") String ligaId) throws IOException {
        pikEvidencija.put(ligaId, 1);

        redosled.put(ligaId, DatabaseBroker.getInstance().vratiSveKorisnikeRandom(ligaId));

        for (int i = 0; i < redosled.get(ligaId).size(); i++) {
            System.out.println((i + 1) + ". " + redosled.get(ligaId).get(i));
        }

        Korisnik k = redosled.get(ligaId).get(0);
        for (Session s : sveLige.get(ligaId)) {
            if (k.getKorisnickoIme().equals(s.getUserProperties().get("korisnickoIme"))) {
                s.getBasicRemote().sendText("Ti si na redu!");
            } else {
                s.getBasicRemote().sendText("Cekaj!");
            }
        }
    }

}
