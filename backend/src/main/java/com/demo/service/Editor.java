package com.demo.service;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class Editor<T,V> {
    ControladorEventos controladorEventos;

    public Editor() {

        LinkedList<String> operations = new LinkedList<>();
        operations.add("actualizada");
        operations.add("creada");
        operations.add("cambio de clave");

        this.controladorEventos = new ControladorEventos(operations);
    }

    public Editor(ControladorEventos controladorEventos) {
        this.controladorEventos = controladorEventos;
    }

    public ControladorEventos getControladorEventos() {
        return controladorEventos;
    }

    public void setControladorEventos(ControladorEventos controladorEventos) {
        this.controladorEventos = controladorEventos;
    }

    //metodo que va a ser llamado a la hora que se actualice el estado de la solicitud
    public void notificacarCambioDeEstado(String tipoDeOperacion,String tipoDeEvento, List<String> correo, V notificacionModulo, ICorreoNotificacionModulo iCorreoNotificacionModulo) throws Exception {
        controladorEventos.notificar(tipoDeOperacion,tipoDeEvento, correo,notificacionModulo, iCorreoNotificacionModulo);
    }



    @Override
    public String toString() {
        return "Editor{" +
                "eventos=" + controladorEventos +
                '}';
    }
}
