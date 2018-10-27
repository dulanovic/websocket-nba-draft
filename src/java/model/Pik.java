package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "pik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pik.findAll", query = "SELECT p FROM Pik p")
    , @NamedQuery(name = "Pik.findByPikId", query = "SELECT p FROM Pik p WHERE p.pikId = :pikId")
    , @NamedQuery(name = "Pik.findByRunda", query = "SELECT p FROM Pik p WHERE p.runda = :runda")
    , @NamedQuery(name = "Pik.findByPik", query = "SELECT p FROM Pik p WHERE p.pik = :pik")})
public class Pik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pik_id")
    private Integer pikId;
    @Column(name = "runda")
    private Integer runda;
    @Column(name = "pik")
    private Integer pik;
    @JoinColumn(name = "igrac", referencedColumnName = "igrac_id")
    @ManyToOne(optional = false)
    private Igrac igrac;
    @JoinColumn(name = "korisnik", referencedColumnName = "korisnik_id")
    @ManyToOne(optional = false)
    private Korisnik korisnik;
    @JoinColumn(name = "liga", referencedColumnName = "liga_id")
    @ManyToOne(optional = false)
    private Liga liga;

    public Pik() {
    }

    public Pik(Integer pikId) {
        this.pikId = pikId;
    }

    public Pik(Integer runda, Integer pik, Igrac igrac, Korisnik korisnik, Liga liga) {
        this.runda = runda;
        this.pik = pik;
        this.igrac = igrac;
        this.korisnik = korisnik;
        this.liga = liga;
    }

    public Integer getPikId() {
        return pikId;
    }

    public void setPikId(Integer pikId) {
        this.pikId = pikId;
    }

    public Integer getRunda() {
        return runda;
    }

    public void setRunda(Integer runda) {
        this.runda = runda;
    }

    public Integer getPik() {
        return pik;
    }

    public void setPik(Integer pik) {
        this.pik = pik;
    }

    public Igrac getIgrac() {
        return igrac;
    }

    public void setIgrac(Igrac igrac) {
        this.igrac = igrac;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pikId != null ? pikId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pik)) {
            return false;
        }
        Pik other = (Pik) object;
        if ((this.pikId == null && other.pikId != null) || (this.pikId != null && !this.pikId.equals(other.pikId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Pik[ pikId=" + pikId + " ]";
    }

}
