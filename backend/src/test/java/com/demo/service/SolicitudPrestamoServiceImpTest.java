package com.demo.service;

import com.demo.entity.*;
import com.demo.repository.RoleRepository;
import com.demo.repository.SolicitudPrestamoRepository;
import com.demo.service.implementations.NotificacionCorreoServiceImp;
import com.demo.service.implementations.SolicitudPrestamoImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SolicitudPrestamoServiceImpTest {

    @Mock
    private SolicitudPrestamoRepository solicitudPrestamoRepository;


    private SolicitudPrestamo solicitud;

    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    private SolicitudPrestamoImp solicitudServiceImp;

    @Mock
    private NotificacionCorreoServiceImp notificacionCorreoServiceImp;

    @BeforeEach
    void setUp() {

        Role roleUser = roleRepository.findByName("USER");

        Set<Role> roles = new HashSet<Role>();

        roles.add(roleUser);

        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setMonto(50000);


       User user = new User();
       user.setId(117220056);
        user.setFirstName("Alfredo");
        user.setLastName("Guerrrro");
        user.setEmail("gmalejan@gmail.com");
        user.setUsername("3117220056");
        user.setPassword("alfredo1234");
        user.setConfirmPassword("alfredo1234");
       // user.setRoles(roles);

        solicitud = new SolicitudPrestamo();
        solicitud.setId(1L);
        solicitud.setUsuario(user);
        solicitud.setEstado("Aceptado");
        solicitud.setFechaCreacion("10-10-22");
        solicitud.setPrestamo(prestamo);

    }

    @Test
    void crearSolicitudPersonal() throws Exception {

        Role roleUser = roleRepository.findByName("USER");

        Set<Role> roles = new HashSet<Role>();

        roles.add(roleUser);

        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setMonto(50000);

        User user1 = new User();
        user1.setId(444444);
        user1.setFirstName("JUAN");
        user1.setLastName("Guerrrro");
        user1.setEmail("jesus.gamboa.mata@est.una.ac.cr");
        user1.setUsername("4444444");
        user1.setPassword("alfredo1234");
        user1.setConfirmPassword("alfredo1234");
       // user1.setRoles(roles);

        SolicitudPrestamo solicitud3 = new SolicitudPrestamo();
        solicitud3.setId(1L);
        solicitud3.setUsuario(user1);
        solicitud3.setEstado("En Espera");
        solicitud3.setFechaCreacion("11-11-22");
        solicitud3.setPrestamo(prestamo);


      //  when(solicitudVacacionRepository.save(ArgumentMatchers.any(SolicitudVacacion.class))).thenReturn(solicitud3);
       // Solicitud created = solicitudServiceImp.crearSolicitudPersonal(solicitud3);
       // assertThat(created.getId()).isSameAs(solicitud.getId());
      //  verify(solicitudVacacionRepository).save(solicitud3);

        //assertNotNull(solicitudServiceImp.crearSolicitudPersonal(solicitud));
    }


    @Test
    void encontrarSolicitudesPorUsuario() {


        Role roleUser = roleRepository.findByName("USER");

        Set<Role> roles = new HashSet<Role>();

        roles.add(roleUser);

        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setMonto(50000);

        Prestamo prestamo2 = new Prestamo();
        prestamo2.setId(2L);
        prestamo2.setMonto(75000);

        User user = new User();
        user.setId(117220056);
        user.setFirstName("Alfredo");
        user.setLastName("Guerrrro");
        user.setEmail("gmalejan@gmail.com");
        user.setUsername("117220056");
        user.setPassword("alfredo1234");
        user.setConfirmPassword("alfredo1234");
       // user.setRoles(roles);

        User user1 = new User();
        user1.setId(444444);
        user1.setFirstName("JUAN");
        user1.setLastName("Guerrrro");
        user1.setEmail("gmalejan@gmail.com");
        user1.setUsername("4444444");
        user1.setPassword("alfredo1234");
        user1.setConfirmPassword("alfredo1234");
      //  user1.setRoles(roles);

        SolicitudPrestamo solicitud1 = new SolicitudPrestamo();
        solicitud1.setId(11L);
        solicitud1.setUsuario(user);
        solicitud1.setEstado("En espera");
        solicitud1.setFechaCreacion("10-10-22");
        solicitud1.setPrestamo(prestamo);

        SolicitudPrestamo solicitud2 = new SolicitudPrestamo();
        solicitud2.setId(2L);
        solicitud2.setUsuario(user1);
        solicitud2.setEstado("En espera");
        solicitud2.setFechaCreacion("10-10-22");
        solicitud1.setPrestamo(prestamo);

        SolicitudPrestamo solicitud3 = new SolicitudPrestamo();
        solicitud3.setId(3L);
        solicitud3.setUsuario(user);
        solicitud2.setEstado("En espera");
        solicitud3.setFechaCreacion("11-11-22");
        solicitud3.setPrestamo(prestamo2);


        List<SolicitudPrestamo> solicituds = new LinkedList<>();
        //solicituds.add(solicitud1);
        solicituds.add(solicitud2);
        solicituds.add(solicitud3);

        List<SolicitudPrestamo> solicitudsExpec = new LinkedList<>();
        solicitudsExpec.add(solicitud3);

       // Iterable<Solicitud> solicituds2 = solicitudServiceImp.encontrarSolicitudesPorUsuario("117220056");


        //Long id = Long.valueOf(11);
        given(solicitudPrestamoRepository.findAll()).willReturn(solicituds);

        List<SolicitudPrestamo> givenFrom =  solicitudServiceImp.encontrarSolicitudesPorUsuario(user);

        assertEquals(solicitudsExpec, givenFrom);
        //verify(solicitudRepository).findByUsuario(user1);

    }


    @Test
    void encontrarSolicitudPorId() {

        Role roleUser = roleRepository.findByName("USER");

        Set<Role> roles = new HashSet<Role>();

        roles.add(roleUser);

        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setMonto(45000);

        User user1 = new User();
        user1.setId(444444);
        user1.setFirstName("JUAN");
        user1.setLastName("Guerrrro");
        user1.setEmail("gmalejan@gmail.com");
        user1.setUsername("4444444");
        user1.setPassword("alfredo1234");
        user1.setConfirmPassword("alfredo1234");
       // user1.setRoles(roles);



        SolicitudPrestamo solicitud2 = new SolicitudPrestamo();
        solicitud2.setId(2L);
        solicitud2.setUsuario(user1);
        solicitud2.setEstado("En espera");
        solicitud2.setFechaCreacion("10-10-22");
        solicitud2.setPrestamo(prestamo);


        SolicitudPrestamo solicitud3 = new SolicitudPrestamo();
        solicitud3.setId(3L);
        solicitud3.setUsuario(user1);
        solicitud3.setEstado("En espera");
        solicitud3.setFechaCreacion("11-11-22");
        solicitud3.setPrestamo(prestamo);

        solicitudPrestamoRepository.save(solicitud2);

        when(solicitudPrestamoRepository.findById(1L)).thenReturn(Optional.ofNullable(solicitud));

        Solicitud expected = solicitudServiceImp.encontrarSolicitudPorId(1L); //con el get se llama la iddentity
        assertThat(solicitud).isSameAs(expected);


    }

    @Test
    void encontrarSolicitudesPorEstadoTrue() {
        Role roleUser = roleRepository.findByName("USER");

        Set<Role> roles = new HashSet<Role>();

        roles.add(roleUser);

        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setMonto(96000);


        User user = new User();
        user.setId(117220056);
        user.setFirstName("Alfredo");
        user.setLastName("Guerrrro");
        user.setEmail("gmalejan@gmail.com");
        user.setUsername("117220056");
        user.setPassword("alfredo1234");
        user.setConfirmPassword("alfredo1234");
       // user.setRoles(roles);

        User user1 = new User();
        user1.setId(444444);
        user1.setFirstName("JUAN");
        user1.setLastName("Guerrrro");
        user1.setEmail("gmalejan@gmail.com");
        user1.setUsername("4444444");
        user1.setPassword("alfredo1234");
        user1.setConfirmPassword("alfredo1234");
       // user1.setRoles(roles);

        SolicitudPrestamo solicitud1 = new SolicitudPrestamo();
        solicitud1.setId(11L);
        solicitud1.setUsuario(user);
        solicitud1.setEstado("Aceptado");
        solicitud1.setFechaCreacion("10-10-22");
        solicitud1.setPrestamo(prestamo);

        SolicitudPrestamo solicitud2 = new SolicitudPrestamo();
        solicitud2.setId(2L);
        solicitud2.setUsuario(user1);
        solicitud2.setEstado("Aceptado");
        solicitud2.setFechaCreacion("10-10-22");
        solicitud2.setPrestamo(prestamo);

        SolicitudPrestamo solicitud3 = new SolicitudPrestamo();
        solicitud3.setId(3L);
        solicitud3.setUsuario(user1);
        solicitud3.setEstado("Aceptado");
        solicitud3.setFechaCreacion("11-11-22");
        solicitud3.setPrestamo(prestamo);
        //solicitudRepository.save(solicitud2);
        solicitudPrestamoRepository.save(solicitud2);

        List<SolicitudPrestamo> solicituds = new LinkedList<>();
        solicituds.add(solicitud1);
        solicituds.add(solicitud2);
        solicituds.add(solicitud3);

        List<SolicitudPrestamo> solicitudsExpec = new LinkedList<>();
        solicitudsExpec.add(solicitud1);
        solicitudsExpec.add(solicitud2);
        solicitudsExpec.add(solicitud3);

        given(solicitudPrestamoRepository.findAll()).willReturn(solicituds);

        List<SolicitudPrestamo> givenFrom =  solicitudServiceImp.encontrarSolicitudesPorEstadoTrue();

        assertEquals(solicitudsExpec, givenFrom);
        //verify(solicitudRepository).findByUsuario(user1);

    }

    @Test
    void findSolicitudByEstadoTrue() {

        //when(solicitudPrestamoRepository.findBy()).thenReturn(solicitud);
        Solicitud returned = solicitudServiceImp.findSolicitudByEstadoTrue(solicitud);
        assertThat(returned.getEstado()).isSameAs("Aceptado");
    }

/*    @Test
    void sendEmail() {



        String toEmail = "alfredoaguerrero1@gmail.com";
        String body = "cuerpo cel correo";
        String subject = "pruebaUnit";
        notificacionCorreoServiceImp.sendEmail(toEmail, subject, body);
        verify(notificacionCorreoServiceImp).sendEmail(toEmail, subject, body);


    }*/


}