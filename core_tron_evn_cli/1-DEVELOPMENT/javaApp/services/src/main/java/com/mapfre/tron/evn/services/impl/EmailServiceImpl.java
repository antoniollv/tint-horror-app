package com.mapfre.tron.evn.services.impl;

import com.mapfre.tron.evn.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private static final String TEXT_HTML = "text/html";
    private static final String CHARSET_UTF_8 = "charset=utf-8";

    @Value("${app.mail.from.name}")
    protected String fromName;

    @Value("${app.mail.from.address}")
    protected String fromAddress;
    @Value("#{${app.mail.to}}")
    protected Map<String, String> to;
    @Value("${app.mail.subject}")
    protected String subject;
    @Value("${app.mail.content}")
    protected String content;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    public void sendMail(String pmSenEml,
                    Map<String, String> pmAdeEmlT,
                    Map<String, String> pmAdeEmlCpyT,
                    Map<String, String> pmAdeEmlHdnT,
                    String pmEmlSbjVal,
                    List<File> pmArhAnxT,
                    String pmMsgTxt,
                    String pmTypCtnBdy) throws Exception {
        try {
            // Definicion mail
            MimeMessage mail = mailSender.createMimeMessage();

            // Se añade el From
            mail.setFrom(new InternetAddress(StringUtils.isNotBlank(fromAddress) ? fromAddress : mailSender.getUsername(), pmSenEml));

            // Se añaden los destinatarios
            if (pmAdeEmlT != null  && !pmAdeEmlT.isEmpty()) {
                for (Map.Entry<String, String> lvAdeEml : pmAdeEmlT.entrySet()) {
                    mail.addRecipient(Message.RecipientType.TO, new InternetAddress(lvAdeEml.getKey(), lvAdeEml.getValue()));
                }
            }

            // Se añaden los destinatarios copia
            if (pmAdeEmlCpyT != null &&  !pmAdeEmlCpyT.isEmpty()) {
                for (Map.Entry<String, String> lvAdeEmlCpy : pmAdeEmlCpyT.entrySet()) {
                    mail.addRecipient(Message.RecipientType.CC, new InternetAddress(lvAdeEmlCpy.getKey(), lvAdeEmlCpy.getValue()));
                }
            }

            // Se añaden los destinatarios copia oculta
            if (pmAdeEmlHdnT != null && !pmAdeEmlHdnT.isEmpty()) {
                for (Map.Entry<String, String> lvAdeEmlHdn : pmAdeEmlHdnT.entrySet()) {
                    mail.addRecipient(Message.RecipientType.BCC, new InternetAddress(lvAdeEmlHdn.getKey(), lvAdeEmlHdn.getValue()));
                }
            }

            // Se añade el asunto
            mail.setSubject(pmEmlSbjVal);

            // Se crea y añade el cuerpo
            MimeMultipart cuerpoAdjuntos = new MimeMultipart();

            if (pmMsgTxt != null) {
                BodyPart texto = new MimeBodyPart();

                if (TEXT_HTML.equalsIgnoreCase(pmTypCtnBdy)) {
                    //Aplica codificacion UTF8 a texto html
                    texto.setContent(pmMsgTxt,pmTypCtnBdy+"; "+CHARSET_UTF_8);
                } else {
                    texto.setText(pmMsgTxt);
                }

                cuerpoAdjuntos.addBodyPart(texto);
            }

            // Se crea y añaden los adjuntos
            if (pmArhAnxT != null && !pmArhAnxT.isEmpty()) {
                for (File lvArhAnx : pmArhAnxT) {
                    BodyPart adjunto = new MimeBodyPart();
                    // Archivo
                    adjunto.setDataHandler(new DataHandler(new FileDataSource(lvArhAnx)));
                    // Nombre Archivo
                    adjunto.setFileName(lvArhAnx.getName());

                    cuerpoAdjuntos.addBodyPart(adjunto);
                }
            }

            // Se asigna adjunto el cuerpo y los adjuntos
            mail.setContent(cuerpoAdjuntos);

            // Envio mail
            mailSender.send(mail);
        } catch (MessagingException | IOException ex) {
            log.error(ex.getMessage(), ex);
            throw(ex);
        }
    }

    @Override
    public void sendMail(Object dto, Exception e) {
        try {
            String body = content + "\r\n" + dto.toString();
            sendMail(fromName, to, null, null, subject, null, body,null);
        } catch (Exception ex) {
            log.error("Error sending email. " + dto.toString(), ex);
        }
    }
}
