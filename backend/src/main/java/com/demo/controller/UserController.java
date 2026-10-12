package com.demo.controller;

import com.demo.dto.ChangePasswordForm;
import com.demo.dto.RecuperarPasswordDTO;
import com.demo.dto.UsuarioDTO;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.service.RoleService;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/")
    public User guardarUsuario(@RequestBody UsuarioDTO usuarioDTO) throws Exception{

      return   userService.createUser(usuarioDTO);
    }


    @PutMapping("/")
    public User actualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) throws Exception{


        return userService.updateUser(usuarioDTO);
    }

    @PutMapping("/actualizar")
    public User cambiarPasswordAdmin(@RequestBody ChangePasswordForm passwordForm) throws Exception{


        return userService.changePasswordAdmin(passwordForm);
    }
    @PutMapping("/actualizar-password")
    public User cambiarPasswordUsuario(@RequestBody ChangePasswordForm passwordForm) throws Exception{


        return userService.changePasswordUser(passwordForm);
    }
    @PutMapping("/recuperar")
    public ResponseEntity<?> recuperarPassword(@RequestBody RecuperarPasswordDTO recuperarPasswordDTO) throws Exception{

            userService.recoverPassword(recuperarPasswordDTO);
        return ResponseEntity.ok(new HttpEntity<>(HttpStatus.OK));
    }

    @GetMapping("/{username}")
    public User obtenerUsuario(@PathVariable("username") String username) throws Exception {
        return userService.getUserByUsername(username);
    }
    @GetMapping("/obtener/{userId}")
    public UsuarioDTO obtenerUsuarioPorId(@PathVariable("userId") Long userId) throws Exception {
        return userService.getUserById(userId);
    }

    @GetMapping("/")
    public List<User> obtenerTodosLosUsuarios() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{usuarioId}")
    public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId) throws Exception {
        userService.deleteUser(usuarioId);
    }

    @GetMapping("/activar/{username}")
    public boolean activarUsuario(@PathVariable("username") String username) throws Exception {

        return userService.enableUser(username);
    }

    @GetMapping("/desactivar/{username}")
    public boolean desactivarUsuario(@PathVariable("username") String username) throws Exception {

        return userService.disableUser(username);
    }

    @GetMapping("/roles")
    public List<Role> obtenerTodosLosRoles(){
        return roleService.getAllRoles();
    }



}
