package com.demo.service;

import java.util.List;

public interface IEventListener<T,V> {
    public void enviarNotificacion(String tipoDeOperacion, String tipoEvento, List<String> correos, V infoDeNotificacion, V infoDeNotificacionAdmin) throws Exception;

}
