package com.demo.excepciones;

public class UsuarioFoundException extends Exception{

    public UsuarioFoundException() {
        super("El usuario con ese username no existe en la base de atos, vuelva a intentar ");
    }

    public UsuarioFoundException(String mensaje){
        super(mensaje);
    }

}