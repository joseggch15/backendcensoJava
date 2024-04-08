/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package Models.Entities;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "persona", catalog = "datacenso", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findByPersonaID", query = "SELECT p FROM Persona p WHERE p.personaID = :personaID"),
    @NamedQuery(name = "Persona.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Persona.findByApellidos", query = "SELECT p FROM Persona p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "Persona.findByFechaNacimiento", query = "SELECT p FROM Persona p WHERE p.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Persona.findByEdad", query = "SELECT p FROM Persona p WHERE p.edad = :edad"),
    @NamedQuery(name = "Persona.findByEstatura", query = "SELECT p FROM Persona p WHERE p.estatura = :estatura"),
    @NamedQuery(name = "Persona.findBySexo", query = "SELECT p FROM Persona p WHERE p.sexo = :sexo"),
    @NamedQuery(name = "Persona.findByNivelEstudios", query = "SELECT p FROM Persona p WHERE p.nivelEstudios = :nivelEstudios"),
    @NamedQuery(name = "Persona.findByDni", query = "SELECT p FROM Persona p WHERE p.dni = :dni")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PersonaID", nullable = false)
    private Integer personaID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Nombre", nullable = false, length = 255)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Apellidos", nullable = false, length = 255)
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "FechaNacimiento", nullable = false, length = 255)
    private String fechaNacimiento;
    @Column(name = "Edad")
    private Integer edad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Estatura", precision = 12, scale = 0)
    private Float estatura;
    @Size(max = 255)
    @Column(name = "Sexo", length = 255)
    private String sexo;
    @Size(max = 255)
    @Column(name = "NivelEstudios", length = 255)
    private String nivelEstudios;
    @Size(max = 15)
    @Column(name = "DNI", length = 15)
    private String dni;
    @JoinColumn(name = "SituacionMilitarID", referencedColumnName = "SituacionMilitarID")
    @ManyToOne
    private Situacionmilitar situacionMilitarID;
    @JoinColumn(name = "LugarNacimientoID", referencedColumnName = "LugarID")
    @ManyToOne
    private Lugar lugarNacimientoID;

    public Persona() {
    }

    public Persona(Integer personaID) {
        this.personaID = personaID;
    }

    public Persona(Integer personaID, String nombre, String apellidos, String fechaNacimiento) {
        this.personaID = personaID;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        
    }

    public Integer getPersonaID() {
        return personaID;
    }

    public void setPersonaID(Integer personaID) {
        this.personaID = personaID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Float getEstatura() {
        return estatura;
    }

    public void setEstatura(Float estatura) {
        this.estatura = estatura;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNivelEstudios() {
        return nivelEstudios;
    }

    public void setNivelEstudios(String nivelEstudios) {
        this.nivelEstudios = nivelEstudios;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Situacionmilitar getSituacionMilitarID() {
        return situacionMilitarID;
    }

    public void setSituacionMilitarID(Situacionmilitar situacionMilitarID) {
        this.situacionMilitarID = situacionMilitarID;
    }

    public Lugar getLugarNacimientoID() {
        return lugarNacimientoID;
    }

    public void setLugarNacimientoID(Lugar lugarNacimientoID) {
        this.lugarNacimientoID = lugarNacimientoID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personaID != null ? personaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.personaID == null && other.personaID != null) || (this.personaID != null && !this.personaID.equals(other.personaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Entities.Persona[ personaID=" + personaID + " ]";
    }
    
}
