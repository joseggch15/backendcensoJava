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

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "lugar", catalog = "datacenso", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lugar.findAll", query = "SELECT l FROM Lugar l"),
    @NamedQuery(name = "Lugar.findByLugarID", query = "SELECT l FROM Lugar l WHERE l.lugarID = :lugarID"),
    @NamedQuery(name = "Lugar.findByNombre", query = "SELECT l FROM Lugar l WHERE l.nombre = :nombre"),
    @NamedQuery(name = "Lugar.findByCodigoUnico", query = "SELECT l FROM Lugar l WHERE l.codigoUnico = :codigoUnico"),
    @NamedQuery(name = "Lugar.findByTipo", query = "SELECT l FROM Lugar l WHERE l.tipo = :tipo")})
public class Lugar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LugarID", nullable = false)
    private Integer lugarID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Nombre", nullable = false, length = 255)
    private String nombre;
    @Size(max = 10)
    @Column(name = "CodigoUnico", length = 10)
    private String codigoUnico;
    @Size(max = 9)
    @Column(name = "Tipo", length = 9)
    private String tipo;
    @OneToMany(mappedBy = "lugarNacimientoID")
    private List<Persona> personaList;
    @JoinColumn(name = "MunicipioID", referencedColumnName = "id")
    @ManyToOne
    private Municipio municipioID;
    @OneToMany(mappedBy = "municipioID1")
    private List<Lugar> lugarList;
    @JoinColumn(name = "MunicipioID", referencedColumnName = "LugarID")
    @ManyToOne
    private Lugar municipioID1;

    public Lugar() {
    }

    public Lugar(Integer lugarID) {
        this.lugarID = lugarID;
    }

    public Lugar(Integer lugarID, String nombre) {
        this.lugarID = lugarID;
        this.nombre = nombre;
    }

    public Integer getLugarID() {
        return lugarID;
    }

    public void setLugarID(Integer lugarID) {
        this.lugarID = lugarID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    public Municipio getMunicipioID() {
        return municipioID;
    }

    public void setMunicipioID(Municipio municipioID) {
        this.municipioID = municipioID;
    }

    @XmlTransient
    public List<Lugar> getLugarList() {
        return lugarList;
    }

    public void setLugarList(List<Lugar> lugarList) {
        this.lugarList = lugarList;
    }

    public Lugar getMunicipioID1() {
        return municipioID1;
    }

    public void setMunicipioID1(Lugar municipioID1) {
        this.municipioID1 = municipioID1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lugarID != null ? lugarID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lugar)) {
            return false;
        }
        Lugar other = (Lugar) object;
        if ((this.lugarID == null && other.lugarID != null) || (this.lugarID != null && !this.lugarID.equals(other.lugarID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Entities.Lugar[ lugarID=" + lugarID + " ]";
    }
    
}
