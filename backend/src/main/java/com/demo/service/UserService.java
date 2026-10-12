package com.demo.service;

import com.demo.dto.ChangePasswordForm;
import com.demo.dto.RecuperarPasswordDTO;
import com.demo.dto.UsuarioDTO;
import com.demo.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User createUser(UsuarioDTO formUser)throws Exception;

    UsuarioDTO getUserById(Long id ) throws Exception;

    User getUserByUsername(String username) throws  Exception;

    User updateUser(UsuarioDTO user) throws Exception;
    User deleteUser(Long id) throws Exception;

    User changePassword(ChangePasswordForm form) throws Exception;
    User changePasswordAdmin(ChangePasswordForm form) throws Exception;
    void deleteUserByUsername(String username);
    User getLoggedUser() throws Exception;

    boolean isLoggedUserADMIN();

    boolean getUserRole(String username) throws Exception;

    boolean disableUser(String username) throws Exception;
    boolean enableUser(String username) throws Exception;

    User recoverPassword(RecuperarPasswordDTO recuperarPasswordDTO) throws Exception;

    User saveUser(User user) throws Exception;
    User changePasswordUser(ChangePasswordForm form) throws Exception;

}
