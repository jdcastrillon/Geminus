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
   })
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
    @Size(min = 1, max = 5)
    @Column(name = "TRANSNOCHO_B")
    private String transnochobool;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "MEDIANOCHE_B")
    private String medianochebool;
    
   
   

    
    
   
    public Geminusturnos() {
    }

    public Geminusturnos(String turno) {
        this.turno = turno;
    }

    public Geminusturnos(String turno, String programacion) {
        this.turno = turno;
        this.programacion = programacion;
    }

    public Geminusturnos(String turno, String programacion, String transnochobool, String medianochebool) {
        this.turno = turno;
        this.programacion = programacion;
        this.transnochobool = transnochobool;
        this.medianochebool = medianochebool;
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
        return "Geminusturnos{" + "turno=" + turno + ", programacion=" + programacion + ", transnochobool=" + transnochobool + ", medianochebool=" + medianochebool  + '}';
    }




    

    public String getTransnochobool() {
        return transnochobool;
    }

    public void setTransnochobool(String transnochobool) {
        this.transnochobool = transnochobool;
    }

    public String getMedianochebool() {
        return medianochebool;
    }

    public void setMedianochebool(String medianochebool) {
        this.medianochebool = medianochebool;
    }

   
    public boolean hasNext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 

 

  
    
    


    
    

   
    
}
