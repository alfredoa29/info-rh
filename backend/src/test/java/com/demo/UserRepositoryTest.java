package com.demo;



import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.repository.RoleRepository;
import com.demo.repository.UserRepository;
import com.demo.service.implementations.UserServiceImpl;
import org.assertj.core.api.Assertions;

import org.junit.Test; //tener cuidado con esta clase

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
    public class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @Autowired(required=false) //necesario https://stackoverflow.com/questions/67345460/how-to-resolve-caused-by-org-springframework-beans-factory-nosuchbeandefinition
     UserServiceImpl userServiceImo;

    private User user;





    @Test
    @Rollback(value = false)
   public void saveUserTest() throws Exception{

//        Role roleUser = roleRepository.findByName("USER");
        
       Set<Role> roles = new HashSet<Role>();
        
       // roles.add(roleUser);

        User user = new User();
        user.setFirstName("Alfredo");
        user.setLastName("Guerrrro");
        user.setEmail("alfredo.guerrero@gmail.com");
        user.setUsername("2117220056");
        user.setPassword("alfredo1234");
        user.setConfirmPassword("alfredo1234");
       // user.setRoles(roles);
//          userRepository.save(user);

     //   assertNotNull(userRepository.findByUsername("2117220056"));
    }

    @Test
    public void getListRoles(){
    //    Iterable<Role> roles = roleRepository.findAll();
    //    System.out.print(roles.toString());
        //Assertions.assertThat(userList.iterator().);
    }


    @Test
    public void getListUsers2(){

//        Role roleUser = roleRepository.findByName("USER");

        Set<Role> roles = new HashSet<Role>();

       // roles.add(roleUser);

        User user1 = new User();
        user1.setFirstName("Emanuel");
        user1.setLastName("Guerrrro");
        user1.setEmail("alfredo.guerrero@gmail.com");
        user1.setUsername("33333");
        user1.setPassword("emanuel03");
        user1.setConfirmPassword("emanuel03");
       // user1.setRoles(roles);
     //   userRepository.save(user1);

       // given(userRepository.findAll()).willReturn(List.of(user, user1));

     //   Iterable<User> userList = userServiceImo.getAllUsers();

    //    assertThat(userList).isNotNull();
     //   assertThat(userList.iterator().equals(2));
        //Assertions.assertThat(userList.iterator().);
    }

    @Test
    public void getUserById(){
      //  Optional<User> user = userRepository.findByUsername("admin");
      //  Assertions.assertThat(user.get().getUsername().equals("admin"));
     //   System.out.print(user.get().toString());
    }

    @Test
    public void eraseUser() throws Exception {

     //   userServiceImo.deleteUser(Long.valueOf(17));
      //  assertThat(userServiceImo.deleteUser(Long.valueOf(17))).isNull();


    }

    @Test
    public void updateUser(){
     //   userRepository.
    }
}
