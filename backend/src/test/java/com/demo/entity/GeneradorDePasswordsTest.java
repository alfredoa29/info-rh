package com.demo.entity;






import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GeneradorDePasswordsTest {


    @Test
    public void  generarPassword() {
        GeneradorDePasswords passGen = new GeneradorDePasswords();
        String password = passGen.generarPassword();

        Assert.assertTrue(password.length() == 10);
    }


}

