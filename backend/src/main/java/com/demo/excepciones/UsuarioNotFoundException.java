package com.demo.excepciones;

public class UsuarioNotFoundException extends Exception{

    public UsuarioNotFoundException() {
        super("El usuario con ese username ya existe en la base de atos, vuelva a intentar ");
    }

    public UsuarioNotFoundException(String mensaje){
        super(mensaje);
    }
}