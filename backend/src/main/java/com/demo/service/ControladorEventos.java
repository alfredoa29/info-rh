package com.demo.service;

import com.demo.service.IEventListener;

import java.util.*;

public class ControladorEventos<T,V> {
    Map<String, List<IEventListener>> listeners = new HashMap<>();

    private ICorreoNotificacionModulo iCorreoNotificacionModulo;
    public ControladorEventos(LinkedList<String> operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new LinkedList<>());
        }
    }


    public Map<String, List<IEventListener>> getListeners() {
        return listeners;
    }

    public void setListeners(Map<String, List<IEventListener>> listeners) {
        this.listeners = listeners;
    }

    //Solas las diferentes clases de alto nivel en las cuales se suscriben y asi envia los corrreos
    //Ejemplo, se llama suscribirrse en NotificacionCorreoServiceImp y se le pasa el tipo de evento, "creada", asi que
    // se busca en la lista ese evento y la clase la cual va a ser escuchada, en este caso la misma clase NotificacionCorreoServiceImp
    //Despues se llama al metodo notificar(en la clase de alto nivel) y se le pasan los datos necesarios par crear la notiicaciion
    //los listeners no son las clases de user o solicitud serviceImp, son las clases de notificaciones, en este caso el unico suscriptor
    //es la clase NotificacionCorreoServiceImp, ya que es el unico que esta implementado, despues se pued implementar algo como NotificacionWebServiceImp
    //y asi sucesivamente, asi que las notificaciones irian a estos dos servicios. Tambien se pude implementar un servicio sms por ejemplo;
    public void suscribirse(String eventType, IEventListener listener){

        List<IEventListener> users = listeners.get(eventType);

        //Aqui literalmente subscribe el el evento, asi que se comprueba que el evento no este en la lista "users" si no va a empezar a enviarr
        //correos repetidos, ya que detecta todos los subscribe anteriores mientras el programa este activo
        if (!users.stream().findAny().isPresent()){
            users.add(listener);
        }





    }

    public void desuscribirse(String eventType, IEventListener listener){
        List<IEventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    public void notificar(String tipoDeOperacion, String tipoEvento, List<String> correos, V notificacionModulo, ICorreoNotificacionModulo iCorreoNotificacionModulo) throws Exception {
        List<IEventListener> users = listeners.get(tipoDeOperacion);
        for (IEventListener listener : users) {

            //los listener son clases de bajo nivel divididas por una interfaz y asi lograr dependcy inversion principle
              listener.enviarNotificacion(tipoDeOperacion, tipoEvento, correos, iCorreoNotificacionModulo.informacionDeLaNotificacion(tipoEvento, notificacionModulo ), iCorreoNotificacionModulo.informacionDeLaNotificacionAdmin(tipoEvento, notificacionModulo));
        }
    }


    @Override
    public String toString() {
        return "ControladorEventos{" +
                "listeners=" + listeners +
                '}';
    }
}
