package com.demo.dto;

import com.demo.entity.Role;

//esta clase es utilizada para mapear objetos que vienen desde el controller y qu neecesitan solamente algunos atributos
//esepecificos
public class UsuarioDTO {

    private long id;

    private String firstName;

    private String lastName;


    private String email;

    private String username;

    private String actualUsername;

    private  String password;

    private String confirmPassword;

    private boolean estado;

    private Role role;

    public UsuarioDTO() {

    }

    public String getActualUsername() {
        return actualUsername;
    }

    public void setActualUsername(String actualUsername) {
        this.actualUsername = actualUsername;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
