package com.demo.service.implementations;

import com.demo.entity.SolicitudPrestamo;
import com.demo.entity.User;
import com.demo.repository.SolicitudPrestamoRepository;
import com.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Primary
@Qualifier("SolicitudPrestamoImp")
@Service
public class SolicitudPrestamoImp implements SolicitudService<SolicitudPrestamo, SolicitudPrestamo> {

    @Autowired

    SolicitudPrestamoRepository solicitudPrestamoRepository;

    @Autowired
    Editor editorNotication;

    @Autowired
    IEventListener notificacionCorreoServiceImp;

    @Autowired
    UserService userService;
    @Autowired
    CorreoServiceImp correoServiceImp;




    @Override
    public SolicitudPrestamo encontrarSolicitudPorId(Long id) {
        return solicitudPrestamoRepository.findById(id).get();
    }

    @Override
    public List<SolicitudPrestamo> encontrarSolicitudesPorUsuario(User user) {

        List<SolicitudPrestamo>  encontrados = new LinkedList<>();
        List<SolicitudPrestamo>  todasSolicitudes =  solicitudPrestamoRepository.findAll();
        for (int i=0; i<todasSolicitudes.size(); i++){
            if (todasSolicitudes.get(i).getUsuario().equals(user)){
                encontrados.add(todasSolicitudes.get(i));
            }
        }



        return encontrados;
    }

    @Override
    public SolicitudPrestamo crearSolicitudPersonal(SolicitudPrestamo solicitudPrestamo) throws Exception {

        List<String> emails = new ArrayList<>();
        ICorreoNotificacionModulo prestamoModulo = new NotificacionPrestamoModulo();
            emails.add(solicitudPrestamo.getUsuario().getEmail());


        try {

             solicitudPrestamoRepository.save(solicitudPrestamo);

            try {
                editorNotication.getControladorEventos().suscribirse("creada", this.notificacionCorreoServiceImp);
                editorNotication.notificacarCambioDeEstado("creada","creada" , emails, solicitudPrestamo, prestamoModulo);

                return solicitudPrestamo;
            }catch (Exception e){
                throw new Exception("Problema al enviar correo al crear la solicitud." + e);
            }

        }catch (Exception e){
            throw new Exception("Problema al crear la solicitud." + e);
        }

    }


    @Override
    public List<SolicitudPrestamo> encontrarSolicitudesPorEstadoTrue() {
        List<SolicitudPrestamo>  encontrados = new LinkedList<>();
        List<SolicitudPrestamo>  todasSolicitudes =  solicitudPrestamoRepository.findAll();
        for (int i=0; i<todasSolicitudes.size(); i++){
            if (todasSolicitudes.get(i).getEstado().equals("Aceptado")){
                encontrados.add(todasSolicitudes.get(i));
            }
        }

        return encontrados;
    }

    @Override
    public List<SolicitudPrestamo> encontrarSolicitudesPorEstadoEnEspera() {
        List<SolicitudPrestamo>  encontrados = new LinkedList<>();
        List<SolicitudPrestamo>  todasSolicitudes =  solicitudPrestamoRepository.findAll();
        for (int i=0; i<todasSolicitudes.size(); i++){
            if (todasSolicitudes.get(i).getEstado().equals("En espera")){
                encontrados.add(todasSolicitudes.get(i));
            }
        }

        return encontrados;
    }

    @Override
    public List<SolicitudPrestamo> encontrarSolicitudesPorEstadoEnEsperayExpirarada() {
        return null;
    }


    @Override
    public SolicitudPrestamo findSolicitudByEstadoTrue(SolicitudPrestamo solicitud) {
        SolicitudPrestamo encontrada = new SolicitudPrestamo();

        if (solicitud.getEstado().equals("Aceptado")){
            encontrada = solicitud;
        }


        return encontrada; ///metodo de prrueba, mejorar
    }

    @Override
    public List<SolicitudPrestamo> encontrarTodos() throws Exception {
        try {
            return solicitudPrestamoRepository.findAll();
        }catch (Exception e){
            throw new Exception("No hay solicitudes");
        }

    }

    @Override
    public List<SolicitudPrestamo> encontrarSolicitudPorTipo(String username, String nombreSolicitud) {
        return null;
    }

    @Override
    public SolicitudPrestamo aceptarSolicitud(Long idSoli) throws Exception {

        List<String> emails = new ArrayList<>();
        ICorreoNotificacionModulo prestamoModulo = new NotificacionPrestamoModulo();

        try {
            SolicitudPrestamo solicitudPrestamo = solicitudPrestamoRepository.findById(idSoli).get();
            solicitudPrestamo.setEstado("Aceptado");
            emails.add(solicitudPrestamo.getUsuario().getEmail());
            solicitudPrestamoRepository.save(solicitudPrestamo);

            try {
                editorNotication.getControladorEventos().suscribirse("actualizada", notificacionCorreoServiceImp);
                editorNotication.notificacarCambioDeEstado("actualizada","aceptada",emails, solicitudPrestamo, prestamoModulo);
                return solicitudPrestamo;
            }catch (Exception e){
                throw new Exception("Problema al enviar correo al acepta la solicitud.");
            }

        }catch (Exception e){
            throw new Exception("Problema al aceptar la solicitud.");
        }

    }

    @Override
    public SolicitudPrestamo rechazarSolicitud(Long idSoli) throws Exception {

        List<String> emails = new ArrayList<>();
        ICorreoNotificacionModulo prestamoModulo = new NotificacionPrestamoModulo();
        try {
            NotificacionCorreoServiceImp notificacionCorreoServiceImp = new NotificacionCorreoServiceImp();
            SolicitudPrestamo solicitudPrestamo = solicitudPrestamoRepository.findById(idSoli).get();
            emails.add(solicitudPrestamo.getUsuario().getEmail());
            solicitudPrestamo.setEstado("Rechazada");
            solicitudPrestamoRepository.save(solicitudPrestamo);
            editorNotication.getControladorEventos().suscribirse("actualizada", this.notificacionCorreoServiceImp);
            editorNotication.notificacarCambioDeEstado("actualizada","Rechazada",emails, solicitudPrestamo, prestamoModulo);
            return solicitudPrestamo;
        }catch (Exception e){
            throw new Exception("Problema al rechazar la solicitud");
        }
    }




}
