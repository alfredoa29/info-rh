package com.demo.service;

import com.demo.dto.UsuarioDTO;
import com.demo.entity.Role;
import com.demo.entity.User;

import com.demo.repository.RoleRepository;
import com.demo.repository.UserRepository;
import com.demo.service.implementations.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.shaded.org.apache.commons.lang.math.RandomUtils;

import javax.persistence.EntityManager;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNull;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    EntityManager entityManagerMock;
@Mock
private UserRepository userRepository;

@Mock
private RoleRepository roleRepository;

@InjectMocks
private UserServiceImpl userService;

@Mock
private User user;

    @Mock
    private UsuarioDTO  usuarioDTO;

    @BeforeEach
    void setUp() throws Exception {

        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("USUARIO");

        roleRepository.save(role);
        roleRepository.save(role2);
        MockitoAnnotations.initMocks(this);
        Role roleUser = roleRepository.findByName("USUARIO");

        Set<Role> roles = new HashSet<Role>();




        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setFirstName("Alfredo");
        usuarioDTO.setLastName("Guerrrro");
        usuarioDTO.setEmail("alfredo.guerrero@gmail.com");
        usuarioDTO.setUsername("117220056");
        usuarioDTO.setPassword("alfredo1234");
        usuarioDTO.setConfirmPassword("alfredo1234");
        usuarioDTO.setRole(role2);

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            final User entity = invocation.getArgument(0);
            ReflectionTestUtils.setField(entity, "id", RandomUtils.nextLong());
            return entity;
        });
         userService.createUser(usuarioDTO);

    }



    @Test
    void createUser() throws Exception {




        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("USUARIO");

        roleRepository.save(role);
        roleRepository.save(role2);
        MockitoAnnotations.initMocks(this);
        Role roleUser = roleRepository.findByName("USUARIO");


        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setFirstName("Alfredo");
        usuarioDTO.setLastName("Guerrrro");
        usuarioDTO.setEmail("alfredo.guerrero@gmail.com");
        usuarioDTO.setUsername("117220056");
        usuarioDTO.setPassword("alfredo1234");
        usuarioDTO.setConfirmPassword("alfredo1234");
        usuarioDTO.setRole(role2);

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            final User entity = invocation.getArgument(0);
            ReflectionTestUtils.setField(entity, "id", RandomUtils.nextLong());
            return entity;
        });

        // Act
        final User user = userService.createUser(usuarioDTO);

        // Assert
        assertThat(user.getUsername()).isNotNull();
        //funciona

    }



    @Test
    void getAllUsers() throws Exception {
        Role roleUser = roleRepository.findByName("USER");

        Set<Role> roles = new HashSet<Role>();

        roles.add(roleUser);

        User user1 = new User();
        user1.setFirstName("Emanuel");
        user1.setLastName("Guerrrro");
        user1.setEmail("alfredo.guerrero@gmail.com");
        user1.setUsername("33333");
        user1.setPassword("emanuel03");
        user1.setConfirmPassword("emanuel03");


       // userService.createUser(user1, usuarioRoles );

        given(userService.getAllUsers()).willReturn(List.of(user, user1));

        Iterable<User> userList = userService.getAllUsers();

        assertThat(userList).isNotNull();
        assertThat(userList.iterator().equals(2));
        Assertions.assertThat(userList.iterator().next());
        //funciona
    }



    @Test
    void getUserByUsername() throws Exception {

        User user1 = new User();
        user1.setFirstName("Emanuel");
        user1.setLastName("Guerrrro");
        user1.setEmail("alfredo.guerrero@gmail.com");
        user1.setUsername("33333");
        user1.setPassword("emanuel03");
        user1.setConfirmPassword("emanuel03");
        //  user1.setRoles(roles);


        Iterable<User> userList = new ArrayList<>();

        userRepository.save(user1);

        //given(userService.getUserByUsername("117220056")).willReturn(user);

      //  when(userService.getUserByUsername("117220056")).thenReturn(user);

       //User expected = userRepository.findByUsername("33333");
       //assertThat(user).isSameAs(expected);

        //no funk
    }
}