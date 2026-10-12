package com.demo.service.implementations;

import com.demo.dto.ChangePasswordForm;
import com.demo.dto.RecuperarPasswordDTO;
import com.demo.dto.UsuarioDTO;
import com.demo.entity.*;
import com.demo.repository.RoleRepository;
import com.demo.repository.UserRepository;
import com.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("UserService")
public class UserServiceImpl implements UserService {

    //inyeccion de dependencia
    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    SolicitudService solicitudService;

    @Autowired
    Editor editorNotication;

    @Autowired
    IEventListener notificacionCorreoServiceImp;

    @Autowired
    private FechaManager fechaManager;


    @Override
    public List<User> getAllUsers() {

        return  repository.findAll();
    }

    //metodo en el cual se chequea si el usuario no esta en la base de datos, si ya se encuentra devuelve un error
    private boolean checkUsernameAvailable(User user) throws Exception {
        User userFound = repository.findByUsername(user.getUsername());
        if (userFound != null) {
            throw new Exception("El usuario ya esta presente");
        }
        return true;
    }
    //metodo en el cual se hace check si el password y check password son las mimas
    private boolean checkPasswordValid(User user) throws Exception {
        if ( !user.getPassword().equals(user.getConfirmPassword())) {
            throw new Exception("Password y Confirm Password no son iguales");
        }
        return true;
    }

    //metodo que guarda el nuevo usuario en la base de datos, ademas, agrega el role a
    //UserRol en la base de datos. Por defecto se guarda como "Normal" en el controller
    @Override
    public User createUser(UsuarioDTO userDTO) throws Exception {
        User saveUser = new User();


             Set<Role> roles = new HashSet<>();
             //aqui busca si el role esta guardado en la base de datos
             //el cual viene desde el frontend como name
             roles.add(roleRepository.findByName(userDTO.getRole().getName()));
             saveUser.setFirstName(userDTO.getFirstName());
             saveUser.setLastName(userDTO.getLastName());
             saveUser.setUsername(userDTO.getUsername());
             saveUser.setPassword(userDTO.getPassword());
             saveUser.setConfirmPassword(userDTO.getConfirmPassword());
             saveUser.setEmail(userDTO.getEmail());
             saveUser.setRoles(roles);
             saveUser.setEstado(userDTO.isEstado());
             BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

             if(checkUsernameAvailable(saveUser) && checkPasswordValid(saveUser)){
                 /*modificar el password para que sea seguro*/
                 saveUser.setPassword(bCryptPasswordEncoder.encode(saveUser.getPassword()));
                 saveUser.setConfirmPassword(bCryptPasswordEncoder.encode(saveUser.getConfirmPassword()));
                 /*modificar el password para que sea seguro*/

                 saveUser = repository.save(saveUser);
             }


        return saveUser;
    }

    @Override
    public UsuarioDTO getUserById(Long id) throws Exception {

        User user = repository.findById(id).orElseThrow(()-> new Exception("El id no existe"));
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(user.getId());
        usuarioDTO.setUsername(user.getUsername());
        usuarioDTO.setFirstName(user.getFirstName());
        usuarioDTO.setLastName(user.getLastName());
        usuarioDTO.setEmail(user.getEmail());
        usuarioDTO.setEstado(user.isEstado());
        if (!user.getRoles().isEmpty()){
            usuarioDTO.setRole(user.getRoles().iterator().next());
        }

        return usuarioDTO;
    }

    @Override
    public User getUserByUsername(String username) throws NoSuchElementException {
        User findUser;
        try {
            findUser = repository.findByUsername(username);
            if (findUser == null) {
                throw new NoSuchElementException("\nEl usuario no existe\n");
            }


            return findUser;
        } catch (Exception e) {

            System.out.println(e.getMessage());


        }

        return null; // null si no encuentra  el usuario
    }


    //metodo que recibe un usuario y lo mapea a user "entity" y actualiza el usuario que ya esta en la base de datos
    @Override
    public User updateUser(UsuarioDTO fromUserDTO) throws Exception {
        User toUser = new User();
        User user = new User();
        Set<Role> roles = new HashSet<>();

        //getActualUsername es el atributo con el  usuario actual que esta en la bd, ya registrado
        //getUsuario   es el atributo con el nuevo id de usuario

        //en este if comprueba si el usuario "actualizado" es el mismo que el usuario actual, si no, entra al else if
        if (fromUserDTO.getUsername().equals(fromUserDTO.getActualUsername()) || repository.findByUsername(fromUserDTO.getUsername())==null){

            //aqui busca si el role esta guardado en la base de datos
            //el cual viene desde el frontend como name
            roles.add(roleRepository.findByName(fromUserDTO.getRole().getName()));
            //se pasa de usuariodto a usuario entity
            user.setFirstName(fromUserDTO.getFirstName());
            user.setLastName(fromUserDTO.getLastName());
            user.setUsername(fromUserDTO.getUsername());
            user.setPassword(fromUserDTO.getPassword());
            user.setConfirmPassword(fromUserDTO.getConfirmPassword());
            user.setEmail(fromUserDTO.getEmail());
            user.setRoles(roles);
            user.setEstado(fromUserDTO.isEstado());

            //toUser es el usuario que ya esta almacenado y user tiene los nuevos datos
             toUser = getUserByUsername(fromUserDTO.getActualUsername());
            //se mapea el usuario ya en la bd y se actualiza con los nuevos datos
            mapUser(user, toUser);

            //--------------------
            //en este if comprueba si el nuevo # de usuario que se quiere actualizar ya esta en la base de datos como
            //otro usuario, si ya esta va a retornar un error, porque si se guardan dos usuarios con el mismo # de id
            //la bd va fallar cuando se quiera solicitar alguna info sobre este usuario
        } else if (repository.findByUsername(fromUserDTO.getUsername())!=null){
            throw new Exception("Error! El username " +fromUserDTO.getUsername()+ " ya se encuentra  registrado en el sistema");

        }


        return repository.save(toUser);
    }
    protected void mapUser(User from, User to){
        to.setUsername(from.getUsername());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setEmail(from.getEmail());
        to.setRoles(from.getRoles());

    }

    @Override
    public User deleteUser(Long id) throws Exception {


        User user =  repository.findById(id).orElseThrow(()-> new Exception("El id no existe"));

        repository.delete(user);
        return user;
    }


    @Override
    public User changePassword(ChangePasswordForm form) throws Exception {
        User user =repository.findById(form.getId()).orElseThrow(()-> new Exception("El id no existe"));

        /*encoder.matches("123456", passwd)*/
        if(!isLoggedUserADMIN() && ! passwordEncoder.matches(form.getCurrentPassword(), user.getPassword())) {
            throw new Exception(" La Password actual es invalida.");
        }
            if (passwordEncoder.matches(form.getNewPassword(), user.getPassword())){
                throw new Exception ("Nuevo debe ser diferente al password actual.");
            }
            if (!form.getNewPassword().equals(form.getConfirmPassword())){
                throw new Exception ("Nuevo Password y Confirm Password no coinciden.");
            }
            String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
            user.setPassword(encodePassword);

        return repository.save(user);
    }

    @Override
    public User changePasswordAdmin(ChangePasswordForm form) throws Exception {
        User user = repository.findById(form.getId()).orElseThrow(()-> new Exception("El id no existe"));

        if (!form.getNewPassword().equals(form.getConfirmPassword())){
            throw new Exception ("ERROR! Nuevo Password y Confirm Password no coinciden.");
        }
        String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
        user.setPassword(encodePassword);

        return repository.save(user);
    }



    @Override
    public void deleteUserByUsername(String username) {
         repository.deleteUserByUsername(username);
    }


    //metodo en el que se comprueba si el usuario que esta ahorita loggeado es admin, este metodo no se utiliza con angular
    //como frontend, ya que se utiliza un token para hacer comprobaciones
    @Override
    public boolean isLoggedUserADMIN() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails loggedUser = null;
        Object roles = null;

        //Verify obj get user session
        if (principal instanceof UserDetails) {
            loggedUser = (UserDetails) principal;
            roles = loggedUser.getAuthorities().stream()
                    .filter(x -> "ROLE_ADMIN".equals(x.getAuthority()))
                    .findFirst()
                    .orElse(null);
        }

        return roles != null;
    }


    //metodo por revisarr
    @Override
    public boolean getUserRole(String username) throws Exception{

        return false;
    }

    @Override
    public boolean disableUser(String username) throws Exception {

        try {

            User userFound = repository.findByUsername(username);
            userFound.setEstado(false);
            repository.save(userFound);
            return  true;

        }catch (Exception e){
            throw new Exception("Problema al deshabilitar el usuario" + e);
        }

    }

    @Override
    public boolean enableUser(String username) throws Exception {

        try {

            User userFound = repository.findByUsername(username);
            userFound.setEstado(true);
            repository.save(userFound);
            return  true;

        }catch (Exception e){
            throw new Exception("Problema al activar el usuario" + e);

        }

    }

    @Override
    public User recoverPassword(RecuperarPasswordDTO recuperarPasswordDTO) throws Exception {

        List<String> emails = new ArrayList<>();

        ICorreoNotificacionModulo seguridadModulo = new NotificacionSeguridadModulo();

        User userChangePass =  this.getUserByUsername(recuperarPasswordDTO.getUsername());
        emails.add(userChangePass.getEmail());
        GeneradorDePasswords generadorDePasswords = new GeneradorDePasswords();
        //se cra un usuario dto para pasarle la info que va a ser enviada por email
        //ya que la clave del User entity (en este caso userChangePass) esta encriptada.
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        String newPassword = generadorDePasswords.generarPassword();
                usuarioDTO.setFirstName(userChangePass.getFirstName());
                usuarioDTO.setLastName(userChangePass.getLastName());
                usuarioDTO.setEmail(userChangePass.getEmail());
                usuarioDTO.setPassword(newPassword);
          String encodedNewPassword   = bCryptPasswordEncoder.encode(newPassword);
        userChangePass.setPassword(encodedNewPassword);


        editorNotication.getControladorEventos().suscribirse("cambio de clave", notificacionCorreoServiceImp);
        editorNotication.notificacarCambioDeEstado("cambio de clave","cambio de clave",emails, usuarioDTO, seguridadModulo);
        return repository.save(userChangePass);
    }


    //metodo que solamente guarda el usuario en la Base de datos y puede ser usado en otras clases de servicio.
    @Override
    public User saveUser(User user) throws Exception {

        return repository.save(user);
    }

    @Override
    public User changePasswordUser(ChangePasswordForm form) throws Exception {
        User user;

        try {

            if (!getLoggedUser().getUsername().equals(form.getUsername())){
                throw new Exception ("ERROR, no se le puede cambiar la clave a otro usuario!");
            }

              user =repository.findByUsername(form.getUsername());

        }catch (Exception e){
            throw e;
        }


        /*encoder.matches("123456", passwd)*/
        if(! passwordEncoder.matches(form.getCurrentPassword(), user.getPassword())) {
            throw new Exception(" La contraseña actual es invalida.");
        }
        if (passwordEncoder.matches(form.getNewPassword(), user.getPassword())){
            throw new Exception ("Nueva contraseña  debe ser diferente a la contraseña actual.");
        }
        if (!form.getNewPassword().equals(form.getConfirmPassword())){
            throw new Exception ("Nueva contraseña y confirmar contraseña no coinciden.");
        }
        String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
        user.setPassword(encodePassword);

        return repository.save(user);
    }


    //metodo en el que se comprueba si el usuario que esta ahorita loggeado es normal, este metodo no se utiliza con angular
    //como frontend, ya que se utiliza un token para hacer comprobaciones
    @Override
    public User getLoggedUser() throws Exception {
        //Obtener el usuario logeado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDetails loggedUser = null;

        //Verificar que ese objeto traido de sesion es el usuario
        if (principal instanceof UserDetails) {
            loggedUser = (UserDetails) principal;
        }

        assert loggedUser != null;
        User myUser = repository
                .findByUsername(loggedUser.getUsername());

        return myUser;
    }






}
