package com.demo;

import com.demo.dto.UsuarioDTO;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.excepciones.UsuarioFoundException;
import com.demo.repository.RoleRepository;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class DemoApplication  implements CommandLineRunner {
    @Autowired

    private UserService userService;

	@Autowired
	private RoleRepository roleRepository;
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

		try{
            Role adminRole = new Role();


                if (roleRepository.findByName("ADMIN")==null){
                   // adminRole.setId(1L);
                    adminRole.setName("ADMIN");
                    roleRepository.save(adminRole);
                    System.out.println("<------------------------------------------------>\n");
                    System.out.println("Se ha creado el role ADMIN la bd\n");
                    System.out.println("<------------------------------------------------>\n");
                }

                    if (roleRepository.findByName("USUARIO")==null){
                        Role usuarioRole = new Role();
                        //adminRole.setId(2L);
                        usuarioRole.setName("USUARIO");
                        roleRepository.save(usuarioRole);
                        System.out.println("<------------------------------------------------>\n");
                        System.out.println("Se ha creado el role USUARIO la bd\n");
                        System.out.println("<------------------------------------------------>\n");
                    }

                    if (userService.getUserByUsername("admin")!=null){
                        System.out.println("<------------------------------------------------>\n");
                        System.out.println("el usuario admin por defecto ya existe...\n");
                        System.out.println("<------------------------------------------------>\n");

                    }else {

                        UsuarioDTO usuarioDTO = new UsuarioDTO();

                        usuarioDTO.setFirstName("Admin");
                        usuarioDTO.setLastName("Admin");
                        usuarioDTO.setUsername("admin");
                        usuarioDTO.setPassword("12345admin");
                        usuarioDTO.setConfirmPassword("12345admin");
                        usuarioDTO.setEmail("admin@example.com");
                        usuarioDTO.setEstado(true);
                        usuarioDTO.setRole(adminRole);

                        User usuarioGuardado = userService.createUser(usuarioDTO);
                        System.out.println(usuarioGuardado.getUsername());

                    }




		}catch (UsuarioFoundException exception){
			exception.printStackTrace();

		}

    }


}
