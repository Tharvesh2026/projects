package dev.tharbyte.MailSender.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Service
public class SimpleMailService {

    @Autowired
    private JavaMailSender mailSender;


    public SimpleMailMessage sendSimpleMail(String from, String to, String sub, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(sub);
        message.setText(content);

        mailSender.send(message);
        return message;
    }

    public MimeMessage sendAttachmentMail(String from, String to, String sub, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(sub);
        helper.setText(content);

        helper.addAttachment(
                "video.mp4",
                new File("C:\\Users\\DS\\Downloads\\YouTube_Shorts_Script_Seco.mp4")
        );
        helper.addAttachment(
                "Notes.pdf",
                new File("C:\\Users\\DS\\Downloads\\OAuth2-vs-OIDC-Field-Note.pdf")
        );

        mailSender.send(message);
        return message;
    }

    public MimeMessage inviteMail(String from,
                                  String to,
                                  String subject,
                                  String templateName)
            throws MessagingException, IOException {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                true,
                StandardCharsets.UTF_8.name()
        );

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);

        ClassPathResource resource =
                new ClassPathResource("templates/" + templateName);

        String html = new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        helper.setText(html, true);

        mailSender.send(message);

        return message;
    }
}
