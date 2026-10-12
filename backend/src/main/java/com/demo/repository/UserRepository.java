package com.demo.repository;

import com.demo.entity.Solicitud;
import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {


    //estos metodos tienen algo interesante, ya que java utiliza la palabra findBy + un atributo da la  itendidad
    //asi que el mismo framework instancia estos metodos
    User findByUsername(String username);
    void deleteUserByUsername(String username);


}
