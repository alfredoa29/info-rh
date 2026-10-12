package com.demo.service.implementations;

import com.demo.entity.CorreoElectronico;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.repository.EmailRepository;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
@Component
public class CorreoServiceImp {


    @Autowired
    EmailRepository emailRepository;

    @Autowired
    UserService userService;

    public void CrearCorreo(CorreoElectronico correoElectronico){
        try {

            emailRepository.save(correoElectronico);

        }catch (Exception e) {
            throw new RuntimeException("Problema al guardar el correo electronico" + e);
        }
    }


    public LinkedList<String> adminEmails() {

        List<CorreoElectronico> correoElectronicos;
        LinkedList<String> correosAdmin = new LinkedList<>();


        String admin = "administrador";
        try {


            correoElectronicos = emailRepository.findAll();



            for (int i=0; i<correoElectronicos.size(); i++){
                if (correoElectronicos.get(i).getTipoDeCorreo().equals(admin)){
                    correosAdmin.add(correoElectronicos.get(i).getCorreoElectronico());
                }
            }

            return correosAdmin;

        } catch (MailSendException e) {
            throw new RuntimeException("Problema al encontrar el correo del administrador"  +e);
        }
    }

    public LinkedList<String> allAdminEmails() {

        List<CorreoElectronico> correoElectronicos;
        LinkedList<String> correosAdmin = new LinkedList<>();

        List<User> userList = userService.getAllUsers();

        String admin = "administrador";
        try {


            correoElectronicos = emailRepository.findAll();

            for (int i =0; i<  userList.size(); i++){

                for (Role role: userList.get(i).getRoles()) {

                    if (role.getName().equals("ADMIN")){
                        correosAdmin.add(userList.get(i).getEmail());
                    }
                }

            }


            for (int i=0; i<correoElectronicos.size(); i++){
                if (correoElectronicos.get(i).getTipoDeCorreo().equals(admin)){
                    correosAdmin.add(correoElectronicos.get(i).getCorreoElectronico());
                }
            }

            return correosAdmin;

        } catch (MailSendException e) {
            throw new RuntimeException("Problema al encontrar el correo del administrador"  +e);
        }
    }


    public List<CorreoElectronico> correoElectronicoList() {

        List<CorreoElectronico> correoElectronicos = new LinkedList<>();
        LinkedList<String> correosAdmin = new LinkedList<>();
        String admin = "administrador";
        try {


            correoElectronicos = emailRepository.findAll();

            for (int i=0; i<correoElectronicos.size(); i++){
                if (correoElectronicos.get(i).getTipoDeCorreo().equals(admin)){
                    correosAdmin.add(correoElectronicos.get(i).getCorreoElectronico());
                }
            }

            return correoElectronicos;

        } catch (MailSendException e) {
            throw new RuntimeException("Problema al encontrar el correo del administrador" + e);
        }
    }

    //Obtiene como entrada el correo, que es un string, se busca en la base de datos como el atributo correo y se devuelve
    //ese objeto correo y se le pasa al metodo delete
    public CorreoElectronico eliminarCorreo(String correo) throws Exception {
        try {
            CorreoElectronico electronico = emailRepository.findByCorreoElectronico(correo);
            emailRepository.delete(electronico);

            return electronico;

        } catch (Exception e) {
            throw new Exception("Problema al eliminar el correo del administrador" + e);
        }
    }


}