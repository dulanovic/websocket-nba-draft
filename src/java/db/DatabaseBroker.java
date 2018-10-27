package db;

import java.util.ArrayList;
import java.util.Collections;
import model.Igrac;
import model.Korisnik;
import model.Liga;
import model.Pik;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class DatabaseBroker {

    private static DatabaseBroker instance;

    private DatabaseBroker() {
    }

    public static DatabaseBroker getInstance() {
        if (instance == null) {
            instance = new DatabaseBroker();
        }

        return instance;
    }

    public Korisnik login(String korisnickoIme, String korisnickaSifra) throws NamingException {
        EntityManager em = EMFactory.createEntityManager();

        try {
            Korisnik k = (Korisnik) em.createNamedQuery("Korisnik.findByKorisnickoImeSifra").setParameter("korisnickoIme", korisnickoIme).setParameter("korisnickaSifra", korisnickaSifra).getSingleResult();
            if (k != null) {
                return k;
            } else {
                return null;
            }
        } catch (NoResultException nrex) {
            nrex.printStackTrace();
            return null;
        } catch (ClassCastException ccex) {
            ccex.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Igrac> nadjiIgrace(String imePrezime) {
        EntityManager em = EMFactory.createEntityManager();
        List<Igrac> lista = em.createNativeQuery("SELECT * FROM igrac i INNER JOIN tim t ON (i.tim = t.tim_id) WHERE ime_prezime LIKE '%" + imePrezime + "%'", Igrac.class).getResultList();
        em.close();
        return lista;
    }

    public List<Igrac> vratiSveIgrace() {
        EntityManager em = EMFactory.createEntityManager();
        List<Igrac> lista = em.createNamedQuery("Igrac.findAll").getResultList();
        em.close();
        return lista;
    }

    public void sacuvajPik(int runda, int pik, int igracId, int korisnikId, int ligaId) {
        EntityManager em = EMFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            Igrac i = em.find(Igrac.class, igracId);
            Korisnik k = em.find(Korisnik.class, korisnikId);
            Liga l = em.find(Liga.class, ligaId);

            Pik p = new Pik(runda, pik, i, k, l);

            em.persist(p);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public List<Korisnik> vratiSveKorisnikeRandom(String ligaId) {
        EntityManager em = EMFactory.createEntityManager();
        List<Korisnik> lista = em.createNativeQuery("SELECT * FROM korisnik WHERE liga = ?ligaId ORDER BY RAND()", Korisnik.class).setParameter("ligaId", ligaId).getResultList();
        List<Korisnik> novaLista = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            if (i % 2 != 0) {
                List<Korisnik> pomocnaLista = new ArrayList<>(lista);
                Collections.reverse(pomocnaLista);
                novaLista.addAll(pomocnaLista);
            } else {
                novaLista.addAll(lista);
            }
        }

        em.close();
        return novaLista;
    }

    public List<Igrac> vratiDostupneIgrace(String ligaId) {
        EntityManager em = EMFactory.createEntityManager();
        List<Igrac> lista = em.createNativeQuery("SELECT * FROM igrac WHERE igrac_id NOT IN (SELECT igrac FROM pik WHERE liga = ?ligaId)", Igrac.class).setParameter("ligaId", ligaId).getResultList();
        em.close();
        return lista;
    }

    public List<Igrac> vratiDostupneIgracePoPoziciji(String ligaId, String pozicija) {
        EntityManager em = EMFactory.createEntityManager();
        List<Igrac> lista = em.createNativeQuery("SELECT * FROM igrac WHERE igrac_id NOT IN (SELECT igrac FROM pik WHERE liga = ?ligaId) AND pozicija = ?pozicija", Igrac.class).setParameter("ligaId", ligaId).setParameter("pozicija", pozicija).getResultList();
        em.close();
        return lista;
    }

}
