package com.demo.service.implementations;

import com.demo.entity.CorreoElectronico;

import com.demo.repository.EmailRepository;
import com.demo.service.ICorreoNotificacionModulo;
import com.demo.service.IEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.LinkedList;
import java.util.List;

@Component
public class NotificacionCorreoServiceImp implements IEventListener<String, String> {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    CorreoServiceImp correoServiceImp;

    @Value("${spring.mail.username}")
    private String from; //esta variable esta en application.properties

    public void sendEmail(List<String> toEmail,String  subject, String body) throws Exception {

        try {
        MimeMessage message = mailSender.createMimeMessage();
        //helper ayuda a poder enviar los correos electrronicos
        MimeMessageHelper helper = new MimeMessageHelper(message);

/*        NotificacionCorreoElectronico notiCorreo = new NotificacionCorreoElectronico();
        notiCorreo.setCorreoRemitente("alfredoaguerrero1@gmail.com");
        notiCorreo.setCorreoDestinatario(toEmail);
        notiCorreo.setSubject(subject);
        notiCorreo.setBody(body);*/

            String[] emailIds = toEmail.toArray(new String[0]);
            //emailIds[0] = notiCorreo.getCorreoRemitente();
            //emailIds[1] = "alfredo.guerrero@cgr.go.cr";

        //SimpleMailMessage message = new SimpleMailMessage();
         helper.setFrom(from);
        helper.setTo(emailIds);
            helper.setSubject(subject);
        helper.setText(body);

        boolean html = true;
        helper.setText(body, html);
          //  mailSender.send(message);
         mailSender.send(helper.getMimeMessage());


        }catch (MailSendException | MessagingException e){
            throw new Exception("Problema al enviar el correo" + e);
        }

    }





    @Override
    public void enviarNotificacion(String tipoDeOperacion, String tipoEvento, List<String> correos, String infoDeNotificacion, String infoDeNotificacionAdmin) throws Exception {

        //en esta parte se envian los correos a los usuarios
        sendEmail(correos," Modulo de Notificaciones -Solicitud " + tipoEvento, infoDeNotificacion);

        if (tipoDeOperacion.equals("creada")){

            if (correoServiceImp.adminEmails().size() != 0  ){
                //en esta parte se envian los correos a los administradores, si hay correos de admins
                sendEmail(correoServiceImp.allAdminEmails()," Modulo de Notificaciones -Solicitud " + tipoEvento, infoDeNotificacionAdmin);
            }



        }


    }
}
