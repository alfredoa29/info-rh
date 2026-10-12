package com.demo.controller;

import com.demo.entity.CorreoElectronico;

import com.demo.service.implementations.CorreoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/configuracion")
public class ConfiguracionController {

    @Autowired
    CorreoServiceImp correoServiceImp;

    @PostMapping("/correos")
    public ResponseEntity<?> guardarCorreo(@RequestBody CorreoElectronico  correoEle) throws Exception {


        try {

            correoServiceImp.CrearCorreo(correoEle);

        }catch (Exception e){
            throw new Exception("Problema al guardar el correo");
        }
        return ResponseEntity.ok(correoEle);
    }


    @GetMapping("/correos")
    public List<String> correoElectronicosAdmin(){

       return correoServiceImp.adminEmails();
    }

    @DeleteMapping("/correos/{correo}")
    public CorreoElectronico eliminarCorreo(@PathVariable("correo") String correo) throws Exception {


                //Correo atributo String, no un objeto correo
         return correoServiceImp.eliminarCorreo(correo);
    }

}
