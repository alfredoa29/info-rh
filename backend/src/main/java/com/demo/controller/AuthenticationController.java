package com.demo.controller;

import com.demo.Configuraciones.JwtUtils;
import com.demo.entity.User;
import com.demo.entity.jwt.JwtRequest;
import com.demo.entity.jwt.JwtResponse;
import com.demo.excepciones.UsuarioFoundException;
import com.demo.service.UserService;
import com.demo.service.implementations.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

    //esta clase pertenece al framework
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @PostMapping("/generate-token")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try{
            autenticar(jwtRequest.getUsername(),jwtRequest.getPassword());
        }catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException("Usuario no encontrado");

        }

        UserDetails userDetails =  this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    //metodo que comprueba el usuario y la contrasena, ademas comprueba si el usuario esta deshabilitaddo
    private void autenticar(String username,String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

            if (userService.getUserByUsername(username).isEstado()==false){
                throw new Exception("USUARIO DESHABILITADO");
            }
        }catch (DisabledException exception){
            throw  new UsuarioFoundException("USUARIO DESHABILITADO " );
        }catch (BadCredentialsException e){
            throw  new UsuarioFoundException("Credenciales invalidas ");
        }
    }

    @GetMapping("/actual-usuario")
    public User obtenerUsuarioActual(Principal principal){
        return (User) this.userDetailsService.loadUserByUsername(principal.getName());
    }
}
