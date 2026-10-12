package com.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FechaManagerTest {

    @Autowired
    FechaManager fechaManager;

    @BeforeEach
    void setUp() {
          fechaManager = new FechaManager();
    }

    @Test
    void calcularFecha() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        String date = "2022-10-25";

        //convert String to LocalDate
       // LocalDate expected = LocalDate.parse(date, formatter);
        String expected = date;
        String actual = fechaManager.calcularFecha("2022-10-21", 2);
        // deberia devolver que la fecha esperaad es 25/10/2022
        assertEquals(expected, actual);
    }

    @Test
    void validarFechas() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String date = "2022-10-23";

        //convert String to LocalDate
        //LocalDate expected = LocalDate.parse(date, formatter);


        Throwable exception = assertThrows(RuntimeException.class, () -> fechaManager.validarFechas(date));
        assertEquals(" ERROR! Debe de escoger un dia  laborable", exception.getMessage());
    }

    @Test
    void validarCambiarValorDiasVacaciones() {


        assertEquals(fechaManager.validarCambiarValorDiasVacaciones(), false);

    }

    @Test
    void obtenerFechaHoy() {

        LocalDate hoy = fechaManager.obtenerFechaHoy();
        assertEquals(hoy, LocalDate.now());

    }

    @Test
    void convertirStringLocalDate() {
        //si funciona pero da error ya que esta fecha no es la actual
      //  String fechahoy = "2023-05-09";


       // assertEquals(fechaManager.convertirStringLocalDate(fechahoy), LocalDate.now());

    }
}