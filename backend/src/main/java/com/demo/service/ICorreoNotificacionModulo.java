package com.demo.service;

public interface ICorreoNotificacionModulo <T,V>{

    public  String informacionDeLaNotificacion(String tipoEvento, V notificacionModulo);
    public  String informacionDeLaNotificacionAdmin(String tipoEvento, V notificacionModulo);
}
