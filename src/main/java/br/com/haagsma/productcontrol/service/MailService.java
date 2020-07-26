package br.com.haagsma.productcontrol.service;

import br.com.haagsma.productcontrol.model.User;
import org.springframework.stereotype.Service;

@Service
public interface MailService {

    void sender(User user, String subject, String body);
}
