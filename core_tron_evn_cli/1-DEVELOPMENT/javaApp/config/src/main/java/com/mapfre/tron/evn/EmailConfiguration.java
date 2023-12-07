package com.mapfre.tron.evn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfiguration {

    @Value("${app.mail.host}")
    protected String host;

    @Value("${app.mail.port}")
    protected int port;

    @Value("${app.mail.username}")
    protected String username;

    @Value("${app.mail.password}")
    protected String password;

    @Bean
    public JavaMailSenderImpl getJavaMailSenderImpl() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

}