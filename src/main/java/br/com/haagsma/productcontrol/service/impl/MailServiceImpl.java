package br.com.haagsma.productcontrol.service.impl;

import br.com.haagsma.productcontrol.model.User;
import br.com.haagsma.productcontrol.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

   @Autowired
   private JavaMailSender mailSender;

    public void sender(User user, String subject, String body) throws Exception {

        MimeMessage mail = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mail);
        helper.setFrom("contato@gmail.com.br", "Empresa X");
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(body);
        mailSender.send(mail);
    }
}
