package com.demo.service.implementations;

import com.demo.entity.SolicitudVacacion;
import com.demo.entity.User;
import com.demo.entity.Vacacion;
import com.demo.repository.SolicitudVacacionRepository;
import com.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Qualifier("SolicitudVacacionImp")
@Service
public class SolicitudVacacionImp implements SolicitudService<SolicitudVacacion, SolicitudVacacion> {

    @Autowired
    SolicitudVacacionRepository solicitudVacacionRepository;

    @Autowired
    Editor editorNotication;

    @Autowired
    IEventListener notificacionCorreoServiceImp;

    @Autowired
    UserService userService;
    @Autowired
    CorreoServiceImp correoServiceImp;




    @Override
    public SolicitudVacacion encontrarSolicitudPorId(Long id) {
        return solicitudVacacionRepository.findById(id).get();
    }

    @Override
    public List<SolicitudVacacion> encontrarSolicitudesPorUsuario(User user) throws Exception {

        List<SolicitudVacacion>  encontrados = new LinkedList<>();
        List<SolicitudVacacion>  todasSolicitudes =  solicitudVacacionRepository.findAll();


        for (int i=0; i<todasSolicitudes.size(); i++){
            if (todasSolicitudes.get(i).getUsuario().equals(user)){
                encontrados.add(todasSolicitudes.get(i));
            }
        }

        comprobarSolicitudesExpiradasPorUsuario(encontrados);

        return encontrados;
    }

    @Override
    public SolicitudVacacion crearSolicitudPersonal(SolicitudVacacion solicitudVacacion) throws Exception {

        List<String> emails = new ArrayList<>();
        ICorreoNotificacionModulo notificacionVacacionModulo = new NotificacionVacacionModulo();
        emails.add(solicitudVacacion.getUsuario().getEmail());


        try {

            //este codigo permite actualizar la cantidad de dias de vacaciones, aunque creo que no se va a utilizar por tiempo
            //pero lo voy a dejar para tal vez en el futuro
            /*
            try {
                User userUpdateDays = solicitudVacacion.getUsuario();
                int updatedCantdiasVacacionesSolicitados =  userUpdateDays.getDiasSolicitados() + solicitudVacacion.getVacacion().getNumDias();
                userUpdateDays.setDiasSolicitados(updatedCantdiasVacacionesSolicitados);

                userService.saveUser(userUpdateDays);

            }catch (Exception e){
                throw new Exception("Problema al guardar y acutializar los dias del usuario");
            }*/


            solicitudVacacionRepository.save(solicitudVacacion);

            try {
                editorNotication.getControladorEventos().suscribirse("creada", this.notificacionCorreoServiceImp);
                editorNotication.notificacarCambioDeEstado("creada","creada" , emails, solicitudVacacion, notificacionVacacionModulo);

                return solicitudVacacion;
            }catch (Exception e){
                throw new Exception("Problema al enviar correo al crear la solicitud." + e);
            }

        }catch (Exception e){
            throw new Exception("Problema al crear la solicitud." + e);
        }

    }


    @Override
    public List<SolicitudVacacion> encontrarSolicitudesPorEstadoTrue() {
        List<SolicitudVacacion>  encontrados = new LinkedList<>();
        List<SolicitudVacacion>  todasSolicitudes =  solicitudVacacionRepository.findAll();
        for (int i=0; i<todasSolicitudes.size(); i++){
            if (todasSolicitudes.get(i).getEstado().equals("Aceptado")){
                encontrados.add(todasSolicitudes.get(i));
            }
        }

        return encontrados;
    }

    @Override
    public List<SolicitudVacacion> encontrarSolicitudesPorEstadoEnEspera() {
        List<SolicitudVacacion>  encontrados = new LinkedList<>();
        List<SolicitudVacacion>  todasSolicitudes =  solicitudVacacionRepository.findAll();
        for (int i=0; i<todasSolicitudes.size(); i++){
            if (todasSolicitudes.get(i).getEstado().equals("En espera")){
                encontrados.add(todasSolicitudes.get(i));
            }
        }

        return encontrados;
    }

    /*
    Metodo que retorna todas las solicitudes que estan en espera y las que estan expiradas al admin
     */

    public List<SolicitudVacacion> encontrarSolicitudesPorEstadoEnEsperayExpirarada() {
        List<SolicitudVacacion>  encontrados = new LinkedList<>();
        List<SolicitudVacacion>  todasSolicitudes =  solicitudVacacionRepository.findAll();
        for (int i=0; i<todasSolicitudes.size(); i++){
            if (todasSolicitudes.get(i).getEstado().equals("En espera") || todasSolicitudes.get(i).getEstado().equals("Expirada") ){
                encontrados.add(todasSolicitudes.get(i));
            }
        }

        return encontrados;
    }

    @Override
    public SolicitudVacacion findSolicitudByEstadoTrue(SolicitudVacacion solicitud) {
        SolicitudVacacion encontrada = new SolicitudVacacion();

        if (solicitud.getEstado().equals("Aceptado")){
            encontrada = solicitud;
        }


        return encontrada; ///metodo de prrueba, mejorar
    }

    @Override
    public List<SolicitudVacacion> encontrarTodos() throws Exception {
        try {
            comprobarSolicitudesExpiradas();
            return solicitudVacacionRepository.findAll();
        }catch (Exception e){
            throw new Exception("No hay solicitudes");
        }

    }

    @Override
    public List<SolicitudVacacion> encontrarSolicitudPorTipo(String username, String nombreSolicitud) {
        return null;
    }

    @Override
    public SolicitudVacacion aceptarSolicitud(Long idSoli) throws Exception {

        List<String> emails = new ArrayList<>();
        ICorreoNotificacionModulo vacacionModulo = new NotificacionVacacionModulo();

        try {



            SolicitudVacacion solicitudVacacion = solicitudVacacionRepository.findById(idSoli).get();
            solicitudVacacion.setEstado("Aceptado");
            emails.add(solicitudVacacion.getUsuario().getEmail());
            solicitudVacacionRepository.save(solicitudVacacion);

            //este codigo permite actualizar la cantidad de dias de vacaciones, aunque creo que no se va a utilizar por tiempo
            //pero lo voy a dejar para tal vez en el futuro
/*            try {
                User userUpdateDays = solicitudVacacion.getUsuario();
                int updatedCantDiasVacacionesSolicitados =  userUpdateDays.getDiasSolicitados() - solicitudVacacion.getVacacion().getNumDias();
                int updateCantDiasVacaciones = userUpdateDays.getCantDiasVacaciones() - solicitudVacacion.getVacacion().getNumDias();
                userUpdateDays.setDiasSolicitados(updatedCantDiasVacacionesSolicitados);
                userUpdateDays.setCantDiasVacaciones(updateCantDiasVacaciones);
                userService.saveUser(userUpdateDays);

            }catch (Exception e){
                throw new Exception("Problema al guardar y acutializar los dias del usuario");
            }*/

            try {
                editorNotication.getControladorEventos().suscribirse("actualizada", notificacionCorreoServiceImp);
                editorNotication.notificacarCambioDeEstado("actualizada","aceptada",emails, solicitudVacacion, vacacionModulo);
                return solicitudVacacion;
            }catch (Exception e){
                throw new Exception("Problema al enviar correo al acepta la solicitud.");
            }



        }catch (Exception e){
            throw new Exception("Problema al aceptar la solicitud.");
        }

    }

    @Override
    public SolicitudVacacion rechazarSolicitud(Long idSoli) throws Exception {

        List<String> emails = new ArrayList<>();
        ICorreoNotificacionModulo vacacionModulo = new NotificacionVacacionModulo();
        try {


            NotificacionCorreoServiceImp notificacionCorreoServiceImp = new NotificacionCorreoServiceImp();
            SolicitudVacacion solicitudVacacion = solicitudVacacionRepository.findById(idSoli).get();
            emails.add(solicitudVacacion.getUsuario().getEmail());
            solicitudVacacion.setEstado("Rechazada");
            solicitudVacacionRepository.save(solicitudVacacion);
            editorNotication.getControladorEventos().suscribirse("actualizada", this.notificacionCorreoServiceImp);
            editorNotication.notificacarCambioDeEstado("actualizada","Rechazada",emails, solicitudVacacion, vacacionModulo);


            //este codigo permite actualizar la cantidad de dias de vacaciones, aunque creo que no se va a utilizar por tiempo
            //pero lo voy a dejar para tal vez en el futuro
/*            try {
                User userUpdateDays = solicitudVacacion.getUsuario();
                int updatedCantdiasVacacionesSolicitados =  userUpdateDays.getDiasSolicitados() - solicitudVacacion.getVacacion().getNumDias();
                userUpdateDays.setDiasSolicitados(updatedCantdiasVacacionesSolicitados);

                userService.saveUser(userUpdateDays);

            }catch (Exception e){
                throw new Exception("Problema al guardar y acutializar los dias del usuario");
            }*/

            return solicitudVacacion;
        }catch (Exception e){
            throw new Exception("Problema al rechazar la solicitud");
        }
    }


    //ve la fecha final de la solicitud y si es esta despues del  la fecha de hoy se pone en expirado
    //este metodo revisa todas las solicitudes en la bd
    public void comprobarSolicitudesExpiradas() throws Exception {

        FechaManager fechaManager = new FechaManager();

        List<SolicitudVacacion> solicitudVacacionListExpiradas = new ArrayList<>();
        List<SolicitudVacacion> todasSolicitudes;
        try {
           todasSolicitudes = solicitudVacacionRepository.findAll();
        }catch (Exception e){
            throw new Exception("No hay solicitudes");
        }

        for (int i=0; i<todasSolicitudes.size(); i++){
            LocalDate fechafinalSoli = fechaManager.convertirStringLocalDate(todasSolicitudes.get(i).getVacacion().getFechaFinal());

            if (fechafinalSoli.isBefore(fechaManager.obtenerFechaHoy())){

                if (todasSolicitudes.get(i).getEstado().equals("En espera") ){
                    todasSolicitudes.get(i).setEstado("Expirada");
                    solicitudVacacionRepository.save(todasSolicitudes.get(i));
                    solicitudVacacionListExpiradas.add(todasSolicitudes.get(i));
                }
            }

        }

    }

    //este metodo comprueba y pone en expiradas las solicitudes cuando son solicitadads por frontend por el usuario
    //solamente revisa las solicituddes del usuario y no todo el set de solicitudes
    public void comprobarSolicitudesExpiradasPorUsuario( List<SolicitudVacacion> soliDelUser) throws Exception {

        FechaManager fechaManager = new FechaManager();

        List<SolicitudVacacion> solicitudVacacionListExpiradas = new ArrayList<>();

        List<SolicitudVacacion> todasSolicitudesDelUsuario = soliDelUser;
        for (int i=0; i<todasSolicitudesDelUsuario.size(); i++){
            LocalDate fechafinalSoli = fechaManager.convertirStringLocalDate(todasSolicitudesDelUsuario.get(i).getVacacion().getFechaFinal());

            if (fechafinalSoli.isBefore(fechaManager.obtenerFechaHoy())){

                if (todasSolicitudesDelUsuario.get(i).getEstado().equals("En espera") || !todasSolicitudesDelUsuario.get(i).getEstado().equals("Expirada") ){
                    todasSolicitudesDelUsuario.get(i).setEstado("Expirada");
                    solicitudVacacionRepository.save(todasSolicitudesDelUsuario.get(i));
                    solicitudVacacionListExpiradas.add(todasSolicitudesDelUsuario.get(i));
                }



               // solicitudVacacionListExpiradas.add(todasSolicitudesDelUsuario.get(i));
            }

        }

    }


}
