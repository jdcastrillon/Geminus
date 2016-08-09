/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "GEMINUSTURNOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Geminusturnos.findAll", query = "SELECT g FROM Geminusturnos g"),
    @NamedQuery(name = "Geminusturnos.findByTurno", query = "SELECT g FROM Geminusturnos g WHERE g.turno = :turno"),
    @NamedQuery(name = "Geminusturnos.findByProgramacion", query = "SELECT g FROM Geminusturnos g WHERE g.programacion = :programacion"),
    @NamedQuery(name = "Geminusturnos.findByTrasnocho", query = "SELECT g FROM Geminusturnos g WHERE g.trasnocho = :trasnocho"),
    @NamedQuery(name = "Geminusturnos.findByMedianoche", query = "SELECT g FROM Geminusturnos g WHERE g.medianoche = :medianoche")})
public class Geminusturnos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TURNO")
    private String turno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PROGRAMACION")
    private String programacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "TRASNOCHO")
    private String trasnocho;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "MEDIANOCHE")
    private String medianoche;

    public Geminusturnos() {
    }

    public Geminusturnos(String turno) {
        this.turno = turno;
    }

    public Geminusturnos(String turno, String programacion, String trasnocho, String medianoche) {
        this.turno = turno;
        this.programacion = programacion;
        this.trasnocho = trasnocho;
        this.medianoche = medianoche;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getProgramacion() {
        return programacion;
    }

    public void setProgramacion(String programacion) {
        this.programacion = programacion;
    }

    public String getTrasnocho() {
        return trasnocho;
    }

    public void setTrasnocho(String trasnocho) {
        this.trasnocho = trasnocho;
    }

    public String getMedianoche() {
        return medianoche;
    }

    public void setMedianoche(String medianoche) {
        this.medianoche = medianoche;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (turno != null ? turno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geminusturnos)) {
            return false;
        }
        Geminusturnos other = (Geminusturnos) object;
        if ((this.turno == null && other.turno != null) || (this.turno != null && !this.turno.equals(other.turno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Geminusturnos[ turno=" + turno + " ]";
    }
    
}
