package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "igrac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Igrac.findAll", query = "SELECT i FROM Igrac i")
    , @NamedQuery(name = "Igrac.findByIgracId", query = "SELECT i FROM Igrac i WHERE i.igracId = :igracId")
    , @NamedQuery(name = "Igrac.findByImePrezime", query = "SELECT i FROM Igrac i WHERE i.imePrezime = :imePrezime")
    , @NamedQuery(name = "Igrac.findByBrojDres", query = "SELECT i FROM Igrac i WHERE i.brojDres = :brojDres")
    , @NamedQuery(name = "Igrac.findByVisina", query = "SELECT i FROM Igrac i WHERE i.visina = :visina")
    , @NamedQuery(name = "Igrac.findByPozicija", query = "SELECT i FROM Igrac i WHERE i.pozicija = :pozicija")
    , @NamedQuery(name = "Igrac.findByIgracSlika", query = "SELECT i FROM Igrac i WHERE i.igracSlika = :igracSlika")})
public class Igrac implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "igrac_id")
    private Integer igracId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ime_prezime")
    private String imePrezime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "broj_dres")
    private String brojDres;
    @Basic(optional = false)
    @NotNull
    @Column(name = "visina")
    private int visina;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "pozicija")
    private String pozicija;
    @Size(max = 100)
    @Column(name = "igrac_slika")
    private String igracSlika;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "igrac")
    private List<Pik> pikList;
    @JoinColumn(name = "tim", referencedColumnName = "tim_id")
    @ManyToOne(optional = false)
    private Tim tim;

    public Igrac() {
    }

    public Igrac(Integer igracId) {
        this.igracId = igracId;
    }

    public Igrac(Integer igracId, String imePrezime, String brojDres, int visina, String pozicija) {
        this.igracId = igracId;
        this.imePrezime = imePrezime;
        this.brojDres = brojDres;
        this.visina = visina;
        this.pozicija = pozicija;
    }

    public Integer getIgracId() {
        return igracId;
    }

    public void setIgracId(Integer igracId) {
        this.igracId = igracId;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    public String getBrojDres() {
        return brojDres;
    }

    public void setBrojDres(String brojDres) {
        this.brojDres = brojDres;
    }

    public int getVisina() {
        return visina;
    }

    public void setVisina(int visina) {
        this.visina = visina;
    }

    public String getPozicija() {
        return pozicija;
    }

    public void setPozicija(String pozicija) {
        this.pozicija = pozicija;
    }

    public String getIgracSlika() {
        return igracSlika;
    }

    public void setIgracSlika(String igracSlika) {
        this.igracSlika = igracSlika;
    }

    @XmlTransient
    public List<Pik> getPikList() {
        return pikList;
    }

    public void setPikList(List<Pik> pikList) {
        this.pikList = pikList;
    }

    public Tim getTim() {
        return tim;
    }

    public void setTim(Tim tim) {
        this.tim = tim;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (igracId != null ? igracId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Igrac)) {
            return false;
        }
        Igrac other = (Igrac) object;
        if ((this.igracId == null && other.igracId != null) || (this.igracId != null && !this.igracId.equals(other.igracId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Igrac[ igracId=" + igracId + " ]";
    }

}
