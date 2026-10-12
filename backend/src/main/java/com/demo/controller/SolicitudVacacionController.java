package com.demo.controller;

import com.demo.entity.SolicitudVacacion;
import com.demo.entity.User;
import com.demo.entity.Vacacion;
import com.demo.service.FechaManager;
import com.demo.service.SolicitudService;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/vacaciones")
@CrossOrigin("*")
public class SolicitudVacacionController {

    @Autowired
    @Qualifier("SolicitudVacacionImp")
    private SolicitudService solicitudVacacionImp;

    @Autowired
    AuthenticationController authenticationController;
    @Autowired
    UserService userService;

    @Autowired
    FechaManager fechaManager;
    @PostMapping("/")
    public SolicitudVacacion guardarSolicitudDeVacacion(@RequestBody Vacacion vacacion) throws Exception {

        try {
            //Fecha fecha = new Fecha(); no se usa, borrar
            User usuarioActual = userService.getLoggedUser();

            SolicitudVacacion solicitudVacacion = new SolicitudVacacion();
            solicitudVacacion.setVacacion(vacacion);
            solicitudVacacion.setUsuario(usuarioActual);
            solicitudVacacion.setTipoSolicitud("Vacaciones");
            solicitudVacacion.setEstado("En espera");

            return (SolicitudVacacion) solicitudVacacionImp.crearSolicitudPersonal(solicitudVacacion);


        } catch (Exception e){
            throw  new Exception("problema al guardar" + e);
        }


    }

    @PutMapping("/calcular")
    public Vacacion calcularFecha(@RequestBody Vacacion vacacion) throws Exception {


        if(fechaManager.validarFechas(vacacion.getFechaInicio())== true  && fechaManager.validarDias(vacacion.getNumDias()) ==true ){
            vacacion.setFechaFinal(fechaManager.calcularFecha(vacacion.getFechaInicio(),vacacion.getNumDias()));


            return vacacion;

        }else {
            vacacion.setFechaFinal("");
        }
        return  vacacion;
    }

    //metodo que devulve todas las solicitudes al admin, ademas, le hace un sort a la lista y devuelve en orden por estado
    @GetMapping("/")
    public List<SolicitudVacacion>listarSolicitudesVacacion() throws Exception {


        List<SolicitudVacacion> solicitudVacacionList = solicitudVacacionImp.encontrarTodos();


        Collections.reverse(solicitudVacacionList);

        return solicitudVacacionList;
    }


    @GetMapping("/solicitudes/{username}")
    public List<SolicitudVacacion> obtenerTodasLasSolicitudesDeVacacionPorUsuario(@PathVariable("username") String username) throws Exception {

       User user =  userService.getUserByUsername(username);
        List<SolicitudVacacion> solicitudVacacionList = solicitudVacacionImp.encontrarSolicitudesPorUsuario(user);
        Collections.reverse(solicitudVacacionList);

        return solicitudVacacionList;

    }

    @GetMapping("/{solicitudVacacionId}")
        public SolicitudVacacion obtenerSolicitudVacacion(@PathVariable("solicitudVacacionId") Long soliVacaId){

        return (SolicitudVacacion) solicitudVacacionImp.encontrarSolicitudPorId(soliVacaId);
        }

    @GetMapping("/aceptar/{solicitudVacacionId}")
    public ResponseEntity<?> aceptarSolicitudVacacion(@PathVariable("solicitudVacacionId") Long soliVacaId) throws Exception {

        return   ResponseEntity.ok(solicitudVacacionImp.aceptarSolicitud(soliVacaId));









    }

    @GetMapping("/rechazar/{solicitudVacacionId}")
    public ResponseEntity<?> rechazarSolicitudVacacion(@PathVariable("solicitudVacacionId") Long soliVacaId) throws Exception {

        return ResponseEntity.ok(solicitudVacacionImp.rechazarSolicitud(soliVacaId));
    }
/*
    @DeleteMapping("/{solicitudVacacionId}")
    public SolicitudVacacion eliminarSolicitudDeVacacion(@PathVariable("solicitudId") Long solicitudVacacionId){

        solicitudService.
    }*/

    @GetMapping("/espera")
    public List<SolicitudVacacion> obtenerTodasLasSolicitudesDeVacacionEspera(){
        List<SolicitudVacacion> solicitudVacacionList = solicitudVacacionImp.encontrarSolicitudesPorEstadoEnEsperayExpirarada();

        return solicitudVacacionList;
    }




}