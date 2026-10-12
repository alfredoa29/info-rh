package com.demo.dto;

import java.io.Serializable;

public class RecuperarPasswordDTO implements Serializable {

    String username;

    public RecuperarPasswordDTO() {
    }

    public RecuperarPasswordDTO(String username) {
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
