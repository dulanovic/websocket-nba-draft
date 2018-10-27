package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "liga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Liga.findAll", query = "SELECT l FROM Liga l")
    , @NamedQuery(name = "Liga.findByLigaId", query = "SELECT l FROM Liga l WHERE l.ligaId = :ligaId")
    , @NamedQuery(name = "Liga.findByNazivLige", query = "SELECT l FROM Liga l WHERE l.nazivLige = :nazivLige")
    , @NamedQuery(name = "Liga.findByVremeOdrzavanjaDrafta", query = "SELECT l FROM Liga l WHERE l.vremeOdrzavanjaDrafta = :vremeOdrzavanjaDrafta")})
public class Liga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "liga_id")
    private Integer ligaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "naziv_lige")
    private String nazivLige;
    @Column(name = "vreme_odrzavanja_drafta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vremeOdrzavanjaDrafta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "liga")
    private List<Pik> pikList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "liga")
    private List<Korisnik> korisnikList;

    public Liga() {
    }

    public Liga(Integer ligaId) {
        this.ligaId = ligaId;
    }

    public Liga(Integer ligaId, String nazivLige) {
        this.ligaId = ligaId;
        this.nazivLige = nazivLige;
    }

    public Integer getLigaId() {
        return ligaId;
    }

    public void setLigaId(Integer ligaId) {
        this.ligaId = ligaId;
    }

    public String getNazivLige() {
        return nazivLige;
    }

    public void setNazivLige(String nazivLige) {
        this.nazivLige = nazivLige;
    }

    public Date getVremeOdrzavanjaDrafta() {
        return vremeOdrzavanjaDrafta;
    }

    public void setVremeOdrzavanjaDrafta(Date vremeOdrzavanjaDrafta) {
        this.vremeOdrzavanjaDrafta = vremeOdrzavanjaDrafta;
    }

    @XmlTransient
    public List<Pik> getPikList() {
        return pikList;
    }

    public void setPikList(List<Pik> pikList) {
        this.pikList = pikList;
    }

    @XmlTransient
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ligaId != null ? ligaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liga)) {
            return false;
        }
        Liga other = (Liga) object;
        if ((this.ligaId == null && other.ligaId != null) || (this.ligaId != null && !this.ligaId.equals(other.ligaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Liga[ ligaId=" + ligaId + " ]";
    }

}
