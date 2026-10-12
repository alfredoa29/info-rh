package com.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
public class Prestamo implements Serializable {

    @NotNull(message = "Debe digitar un monto")
    @Column
    private Integer monto;

    @Id
    @GeneratedValue
    private Long id;

    public Prestamo() {
    }

    public Prestamo(Integer monto){
        this.monto = monto;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "monto=" + '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
