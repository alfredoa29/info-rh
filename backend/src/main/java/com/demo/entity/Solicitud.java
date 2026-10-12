package com.demo.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Solicitud implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    protected Long id;



   // @JoinColumn(name = "usuario_id", nullable = false)
   @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
   protected User usuario;

    @Column
    protected String fechaCreacion;

    @Column
    protected String estado ;

    protected String tipoSolicitud; //si es vacacion u otro tipo


    public Solicitud() {
    FechaActual fechaActual = new FechaActual();
     this.fechaCreacion = fechaActual.getFechaActual();

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fecha) {
        this.fechaCreacion = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }
}
