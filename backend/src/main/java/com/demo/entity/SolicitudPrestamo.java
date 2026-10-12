package com.demo.entity;



import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SolicitudPrestamo extends Solicitud implements Serializable {

    @ManyToOne(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinColumn(name = "prestamo_id")
    Prestamo prestamo;

    public SolicitudPrestamo(){
    }

    public Prestamo getPrestamo(){return prestamo;}

    public void setPrestamo(Prestamo prestamo){this.prestamo = prestamo;}

    @Override
    public String toString() {
        return "SolicitudPrestamo{" +
                "prestamo=" + prestamo + '}';
    }
}
