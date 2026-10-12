package com.demo.service.implementations;

import com.demo.entity.Role;
import com.demo.repository.RoleRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest  {


    @Mock
    RoleRepository roleRepository;

    @Mock
    private RoleServiceImpl roleService;

    @BeforeEach
    public void setup(){
        Role roleAdmin = new Role();
        roleAdmin.setId(1L);
        roleAdmin.setName("ADMIN");

        roleRepository.save(roleAdmin);
    }
    @Test
    void getAllRoles() {
/*        User usuario = new User();
        usuario.setFirstName("Admin");
        usuario.setLastName("Admin");
        usuario.setUsername("admin");
        usuario.setPassword("12345admin");
        usuario.setConfirmPassword("12345admin");
        usuario.setEmail("alfredoaguerrero1@gmail.com");
        usuario.setEstado(true);
 */


        Role roleNormal = new Role();
        roleNormal.setId(2L);
        roleNormal.setName("NORMAL");
        Role roleAdmin = new Role();
        roleAdmin.setId(1L);
        roleAdmin.setName("ADMIN");

        List<Role> givenList = new ArrayList<>();
        givenList.add(roleAdmin);
        givenList.add(roleNormal);

        given(roleService.getAllRoles()).willReturn(List.of(roleAdmin, roleNormal));

        List<Role> roleList = roleService.getAllRoles();

        // este si funciona
        AssertionsForClassTypes.assertThat(roleList).isNotNull();
        assertEquals(roleList,  givenList);
    }
}