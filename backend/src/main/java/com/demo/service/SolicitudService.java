package com.demo.service;

import com.demo.entity.User;

import java.util.List;

public interface SolicitudService<T,V> {

    public T encontrarSolicitudPorId(Long id);
    public List<T> encontrarSolicitudesPorUsuario(User user) throws Exception;

        public T crearSolicitudPersonal(V solicitud) throws Exception;

    public List<T> encontrarSolicitudesPorEstadoTrue();
    public List<T> encontrarSolicitudesPorEstadoEnEspera();
    public List<T> encontrarSolicitudesPorEstadoEnEsperayExpirarada();
    public  T findSolicitudByEstadoTrue(T solicitud);

    public List<T> encontrarTodos() throws Exception;
    public  List<T> encontrarSolicitudPorTipo(String username, String nombreSolicitud);

    public T aceptarSolicitud(Long idSoli) throws Exception;
    public  T rechazarSolicitud(Long idSoli) throws Exception;


}
