/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author admin
 */
public class Compensado {
    private String Trabajador;
    private String nombre;
    private int dias;
    private int numero;
    private Date fecha;
    

    public Compensado() {
    }

    public Compensado(int numero,String Trabajador, String nombre, int dias) {
        this.Trabajador = Trabajador;
        this.nombre = nombre;
        this.dias = dias;
        this.numero=numero;
    }

    public Compensado(String Trabajador, int dias, Date fecha) {
        this.Trabajador = Trabajador;
        this.dias = dias;
        this.fecha = fecha;
    }

 
    

    public String getTrabajador() {
        return Trabajador;
    }

    public void setTrabajador(String Trabajador) {
        this.Trabajador = Trabajador;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    @Override
    public String toString() {
        return "Compensado{" + "Trabajador=" + Trabajador + ", dias=" + dias + '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
  

  
    
}
