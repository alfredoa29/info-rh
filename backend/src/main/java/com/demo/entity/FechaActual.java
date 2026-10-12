package com.demo.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaActual {

    private String fechaActual;

    public FechaActual() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(date);
        formatter = new SimpleDateFormat("dd MMMM yyyy");
        strDate = formatter.format(date);

        this.fechaActual = strDate;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }
}
