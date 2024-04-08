/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "situacionmilitar", catalog = "datacenso", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Situacionmilitar.findAll", query = "SELECT s FROM Situacionmilitar s"),
    @NamedQuery(name = "Situacionmilitar.findBySituacionMilitarID", query = "SELECT s FROM Situacionmilitar s WHERE s.situacionMilitarID = :situacionMilitarID"),
    @NamedQuery(name = "Situacionmilitar.findByDescripcion", query = "SELECT s FROM Situacionmilitar s WHERE s.descripcion = :descripcion")})
public class Situacionmilitar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SituacionMilitarID", nullable = false)
    private Integer situacionMilitarID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Descripcion", nullable = false, length = 255)
    private String descripcion;
    @OneToMany(mappedBy = "situacionMilitarID")
    private List<Persona> personaList;

    public Situacionmilitar() {
    }

    public Situacionmilitar(Integer situacionMilitarID) {
        this.situacionMilitarID = situacionMilitarID;
    }

    public Situacionmilitar(Integer situacionMilitarID, String descripcion) {
        this.situacionMilitarID = situacionMilitarID;
        this.descripcion = descripcion;
    }

    public Integer getSituacionMilitarID() {
        return situacionMilitarID;
    }

    public void setSituacionMilitarID(Integer situacionMilitarID) {
        this.situacionMilitarID = situacionMilitarID;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (situacionMilitarID != null ? situacionMilitarID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Situacionmilitar)) {
            return false;
        }
        Situacionmilitar other = (Situacionmilitar) object;
        if ((this.situacionMilitarID == null && other.situacionMilitarID != null) || (this.situacionMilitarID != null && !this.situacionMilitarID.equals(other.situacionMilitarID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Entities.Situacionmilitar[ situacionMilitarID=" + situacionMilitarID + " ]";
    }
    
}
