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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "tim")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tim.findAll", query = "SELECT t FROM Tim t")
    , @NamedQuery(name = "Tim.findByTimId", query = "SELECT t FROM Tim t WHERE t.timId = :timId")
    , @NamedQuery(name = "Tim.findByTimNaziv", query = "SELECT t FROM Tim t WHERE t.timNaziv = :timNaziv")
    , @NamedQuery(name = "Tim.findByTimLogo", query = "SELECT t FROM Tim t WHERE t.timLogo = :timLogo")})
public class Tim implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tim_id")
    private Integer timId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "tim_naziv")
    private String timNaziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "tim_logo")
    private String timLogo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tim")
    private List<Igrac> igracList;

    public Tim() {
    }

    public Tim(Integer timId) {
        this.timId = timId;
    }

    public Tim(Integer timId, String timNaziv, String timLogo) {
        this.timId = timId;
        this.timNaziv = timNaziv;
        this.timLogo = timLogo;
    }

    public Integer getTimId() {
        return timId;
    }

    public void setTimId(Integer timId) {
        this.timId = timId;
    }

    public String getTimNaziv() {
        return timNaziv;
    }

    public void setTimNaziv(String timNaziv) {
        this.timNaziv = timNaziv;
    }

    public String getTimLogo() {
        return timLogo;
    }

    public void setTimLogo(String timLogo) {
        this.timLogo = timLogo;
    }

    @XmlTransient
    public List<Igrac> getIgracList() {
        return igracList;
    }

    public void setIgracList(List<Igrac> igracList) {
        this.igracList = igracList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (timId != null ? timId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tim)) {
            return false;
        }
        Tim other = (Tim) object;
        if ((this.timId == null && other.timId != null) || (this.timId != null && !this.timId.equals(other.timId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Tim[ timId=" + timId + " ]";
    }

}
