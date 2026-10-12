package com.demo.service;

import com.demo.entity.User;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
@Service
public class FechaManager {

    @Autowired
    UserService userService;

    public String calcularFecha(String fecha, int days){

        String cadena = fecha;
        String[] fragmentos = cadena.split("-");
        String anniodeUltimo = fragmentos[2]+"/"+ fragmentos[1] +"/"+ fragmentos[0];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = anniodeUltimo;
        //convierte String to LocalDate
        LocalDate givenDate = LocalDate.parse(date, formatter);

        DayOfWeek dayOfWeek = DayOfWeek.SUNDAY;


            LocalDate result = givenDate;
            int addedDays = 0;
            while (addedDays < days) {
                result = result.plusDays(1);
                if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    ++addedDays;
                }

            }
            return result.toString();

    }

   public boolean validarFechas(String fecha){
        //esta parte divide la fecha y le da formato d/MM/yyyy y no yyyy/MM/d
        String cadena = fecha;
        String[] fragmentos = cadena.split("-");
        String anniodeUltimo = fragmentos[2]+"/"+ fragmentos[1] +"/"+ fragmentos[0];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String date = anniodeUltimo;
        //convert String to LocalDate
        LocalDate givenDate = LocalDate.parse(date, formatter);

        DayOfWeek dayOfWeek = DayOfWeek.SUNDAY;

        boolean fechaBoolean = true;
        if (givenDate.getDayOfWeek().equals(dayOfWeek)){
            fechaBoolean = false;
            throw new RuntimeException(" ERROR! Debe de escoger un dia  laborable");



        }

        return fechaBoolean;
    }

   public boolean validarDias(int dias) throws Exception {

        boolean diasBoolean = true;

        //if (dias>15 || !validarUsuarioTieneDiasDisponibles(dias)==true){
       if (dias>15 ){
            diasBoolean = false;
           // throw new RuntimeException(" ERROR! Numero de dia no valido");
        }


        return diasBoolean;
    }


    public boolean validarCambiarValorDiasVacaciones(){

        LocalDate hoy = LocalDate.now();
        LocalDate ultimoDiaYear =  LocalDate.now().with(lastDayOfYear());

         if (hoy.equals(ultimoDiaYear)){

             return true;
         }
         return false;
    }

    public LocalDate obtenerFechaHoy(){

        LocalDate hoy = LocalDate.now();


        return hoy;
    }

    public LocalDate convertirStringLocalDate(String fecha){

        String cadena = fecha;
        String[] fragmentos = cadena.split("-");
        String anniodeUltimo = fragmentos[2]+"/"+ fragmentos[1] +"/"+ fragmentos[0];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = anniodeUltimo;
        //convierte String to LocalDate
        LocalDate givenDate = LocalDate.parse(date, formatter);

            return givenDate;
    }

        //metodo para validad dias, por el momento no se va  utilizar, tal vez en el futuro
/*    public boolean validarUsuarioTieneDiasDisponibles( int dias) throws Exception {

       User validarUser= userService.getLoggedUser();

        try {
            int calcularDiasSolicitados = validarUser.getDiasSolicitados() + dias;

            if (validarUser.getCantDiasVacaciones() <=15 && calcularDiasSolicitados <=15){

                return true;
            }else {
                return  false;
                //throw new Exception("Error! Usuario no tine esta cantidad de dias disponibles");
            }

        }catch (Exception e){
            throw new Exception("Error! En validar dias disponibles para el usuario");
        }

    }*/


}
