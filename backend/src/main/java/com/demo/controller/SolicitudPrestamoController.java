package com.demo.controller;

import com.demo.entity.SolicitudPrestamo;
import com.demo.entity.User;
import com.demo.entity.Prestamo;

import com.demo.service.SolicitudService;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/prestamos")
public class SolicitudPrestamoController {

    @Autowired
    @Qualifier("SolicitudPrestamoImp")
    private SolicitudService solicitudPrestamoImp;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    UserService userService;

    @PostMapping("/")
    public SolicitudPrestamo guardarSolicitudDePrestamo(@RequestBody Prestamo prestamo) throws Exception {

        try {
            User usuarioActual = userService.getLoggedUser();
            SolicitudPrestamo solicitudPrestamo = new SolicitudPrestamo();
            solicitudPrestamo.setPrestamo(prestamo);
            solicitudPrestamo.setUsuario(usuarioActual);
            solicitudPrestamo.setTipoSolicitud("Prestamos");
            solicitudPrestamo.setEstado("En espera");

            return (SolicitudPrestamo) solicitudPrestamoImp.crearSolicitudPersonal(solicitudPrestamo);


        } catch (Exception e){
            throw new Exception("problema al guardar");
        }

    }

    @GetMapping("/")
    public List<SolicitudPrestamo>listarSolicitudesPrestamo() throws Exception {


        List<SolicitudPrestamo> solicitudPrestamoList = solicitudPrestamoImp.encontrarTodos();


        Collections.reverse(solicitudPrestamoList);

        return solicitudPrestamoList;
    }

    @GetMapping("/solicitudesPrestamo/{username}")
    public List<SolicitudPrestamo> obtenerTodasLasSolicitudesDePrestamoPorUsuario(@PathVariable("username") String username) throws Exception {

        User user =  userService.getUserByUsername(username);
        List<SolicitudPrestamo> solicitudPrestamoList = solicitudPrestamoImp.encontrarSolicitudesPorUsuario(user);
        Collections.reverse(solicitudPrestamoList);

        return solicitudPrestamoList;

    }

    @GetMapping("/{solicitudPrestamoId}")
    public SolicitudPrestamo obtenerSolicitudPrestamo(@PathVariable("solicitudPrestamoId") Long soliPresId){

        return (SolicitudPrestamo) solicitudPrestamoImp.encontrarSolicitudPorId(soliPresId);
    }

    @GetMapping("/aceptar/{solicitudPrestamoId}")
    public ResponseEntity<?> aceptarSolicitudPrestamo(@PathVariable("solicitudPrestamoId") Long soliPresId) throws Exception {

        return   ResponseEntity.ok(solicitudPrestamoImp.aceptarSolicitud(soliPresId));
    }

    @GetMapping("/rechazar/{solicitudPrestamoId}")
    public ResponseEntity<?> rechazarSolicitudPrestamo(@PathVariable("solicitudPrestamoId") Long soliPresId) throws Exception {

        return ResponseEntity.ok(solicitudPrestamoImp.rechazarSolicitud(soliPresId));
    }

    /*
    @DeleteMapping("/{solicitudPrestamoId}")
    public SolicitudPrestamo eliminarSolicitudDePrestamo(@PathVariable("solicitudId") Long soliPresId){

        solicitudService.
    }*/

    @GetMapping("/espera")
    public List<SolicitudPrestamo> obtenerTodasLasSolicitudesDePrestamoEspera(){
        List<SolicitudPrestamo> solicitudPrestamoList = solicitudPrestamoImp.encontrarSolicitudesPorEstadoEnEspera();

        return solicitudPrestamoList;
    }

}
