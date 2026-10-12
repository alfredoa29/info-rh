package com.demo.service;

import com.demo.dto.UsuarioDTO;
import com.demo.entity.User;

public class NotificacionSeguridadModulo implements ICorreoNotificacionModulo<UsuarioDTO, UsuarioDTO>{
    @Override
    public String informacionDeLaNotificacion(String tipoEvento, UsuarioDTO usuario) {
        String InformacionDeLaNotificacion =
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "<title>Page Title</title>\n" +
                        "<meta charset=\"UTF-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                        "<style>\n" +
                        "/* Style the body */\n" +
                        "body {\n" +
                        "  font-family: Helvetica;\n" +
                        "  margin: 0;\n" +
                        "}\n" +
                        "\n" +
                        "/* Header/Logo Title */\n" +
                        ".header {\n" +
                        "  padding: 1px;\n" +
                        "  text-align: center;\n" +
                        "  background: #FFA500;\n" +
                        "  color: white;\n" +
                        "  font-size: 13px;\n" +
                        "}\n" +
                        "\n" +
                        "p.footer{\n" +
                        "\n" +
                        "font-size: 12px;\n" +
                        "\n" +
                        "}\n" +
                        "\n" +
                        "/* Page Content */\n" +
                        ".content {\n" +
                        "padding:10px;\n" +
                        "background-color:  #FFFFFF;\n" +
                        "\n" +
                        "}\n" +
                        "\n" +
                        "p.light {\n" +
                        "font-size: 14px;\n" +
                        "  font-weight: lighter;\n" +
                        "  color: #4A4A4A;\n" +
                        "}\n" +
                        "</style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<div class=\"header\">\n" +
                        "  <p>Estimado(a) Usuario(a): El Quiosco INFO-RH le informa:</p>\n" +
                        "</div>\n" +
                        "\n" +
                        "<div class=\"content\">\n" +
                        " <p class=\"light\">Estimado(a) "+ usuario.getFirstName() +" "+ usuario.getLastName() +".</p>\n" +
                        "  \n" +
                        " \n" +
                        " <p class=\"light\">Se le informa que ha solicitado un <strong> "+ tipoEvento+ "\n" +
                        " :\"</strong> </p>\n" +
                        " <p class=\"light\" ><b>Nueva contraseña:</b> "+ usuario.getPassword()+  " .</p>\n" +
                        " \n" +
                        "<p class=\"light\"> Puede verificarlo en el Quiosco informativo.</p>\n" +
                        " \n" +
                        "<hr>\n" +
                        " <p class=\" footer\">Este correo es de tipo informativo, por favor no responda a este mensaje. </p>\n" +
                        "\n" +
                        "</div>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html> ";
        return InformacionDeLaNotificacion;

    }

    @Override
    public String informacionDeLaNotificacionAdmin(String tipoEvento, UsuarioDTO notificacionModulo) {
        return null;
    }


}
